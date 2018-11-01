package eu.ggam.container.impl.servletcontainer.descriptor.materialized;

import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.FilterMappingMetamodel;
import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.ServletMappingMetamodel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import static java.util.stream.Collectors.flatMapping;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;

/**
 *
 * @author Guillermo González de Agüero
 */
public class UrlPatternsParser {
//new TreeSet<>(MatchType.EXACT, MatchType.EXTENSION, MatchType.PREFIX);

    private final List<KnownUrlPattern> knownPatterns = new ArrayList<>();

    public UrlPatternsParser(Set<ServletMappingMetamodel> mappings, List<FilterMappingMetamodel> filterMappings) {
        validateDuplications(mappings);

        // Accumulate Servlet URL mappings
        // Key: Servlet name - Value: URL pattern
        Map<String, Set<String>> servletNameUrlPatterns = mappings.stream().
                collect(groupingBy(ServletMappingMetamodel::getServletName,
                        flatMapping(m -> m.getUrlPatterns().stream(), toSet())));

        // Accumulate Filter URL mappings
        // Key: URL pattern - Value: Filter name
        Map<String, List<String>> urlPatternFilters = filterMappings.stream().
                map(fm -> {
                    // Convert Servlet names into URL patterns

                    if (fm.getServletNames().isEmpty()) {
                        // No need to convert servlet names
                        return fm;
                    }

                    Set<String> filterUrlPatterns = fm.getServletNames().
                            stream().
                            map(s -> {
                                if (!servletNameUrlPatterns.containsKey(s)) {
                                    throw new IllegalArgumentException("Servlet \"" + s + "\" does not exist");
                                }

                                return servletNameUrlPatterns.get(s);
                            }).
                            flatMap(Set::stream).
                            collect(toSet());
                    filterUrlPatterns.addAll(fm.getUrlPatterns());

                    return new FilterMappingMetamodel(fm.getFilterName(), filterUrlPatterns, Collections.emptySet());
                }).
                collect(groupingBy(FilterMappingMetamodel::getFilterName,
                        flatMapping(m -> m.getUrlPatterns().stream(), toList())));

        // Servlets + associated filters
        for (Map.Entry<String, Set<String>> servletNameUrlPattern : servletNameUrlPatterns.entrySet()) {
            String servletName = servletNameUrlPattern.getKey();

            for (String urlPattern : servletNameUrlPattern.getValue()) {
                knownPatterns.add(new KnownUrlPattern(urlPattern, servletName, urlPatternFilters.containsKey(urlPattern) ? urlPatternFilters.remove(urlPattern) : Collections.emptyList()));
            }
        }

        // Filters mapped to URLs with no associated Servlet. The default Servlet will handle those requests
        for (Map.Entry<String, List<String>> urlPatternFilter : urlPatternFilters.entrySet()) {
            knownPatterns.add(new KnownUrlPattern(urlPatternFilter.getKey(), null, urlPatternFilter.getValue()));
        }

        // Ensure correct order
        Collections.sort(knownPatterns, Comparator.comparing(u -> u.getMatchingPattern().getMatchType()));
    }

    public RequestUriMatch match(String uriWithoutContextPath) {
        for (KnownUrlPattern urlPattern : knownPatterns) {
            Optional<MatchingPattern.ServletUriMatch> servletUriMatch = urlPattern.getMatchingPattern().uriMatch(uriWithoutContextPath);
            if (servletUriMatch.isPresent()) {
                MatchingPattern.ServletUriMatch match = servletUriMatch.get();
                return new RequestUriMatch(urlPattern.getServletName(), urlPattern.getFilterNames(), urlPattern.getMatchingPattern(), match.getServletPath(), match.getPathInfo());
            }
        }

        throw new IllegalStateException("No Servlet was registered to handle URI \"" + uriWithoutContextPath + "\"."
                + "This is a configuration error since a default Servlet must always be present");
    }

    private void validateDuplications(Set<ServletMappingMetamodel> mappings) {
        // Validate duplicated Servlet names
        long count = mappings.stream().
                map(ServletMappingMetamodel::getServletName).
                distinct().
                count();
        if (count != mappings.size()) {
            throw new IllegalArgumentException("Duplicated Servlet names");
        }

        // Validate duplicated URL patterns
        long distinctUrls = toUrlStream(mappings).distinct().count();
        long totalUrls = toUrlStream(mappings).count();
        if (distinctUrls != totalUrls) {
            throw new IllegalArgumentException("Duplicated URL patterns");
        }
    }

    private Stream<String> toUrlStream(Set<ServletMappingMetamodel> mappings) {
        return mappings.stream().
                map(ServletMappingMetamodel::getUrlPatterns).
                flatMap(Set::stream);
    }
}
