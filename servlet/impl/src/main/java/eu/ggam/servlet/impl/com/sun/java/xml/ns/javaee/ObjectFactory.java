//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0-b170531.0717 
// Visite <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.01.03 a las 04:45:20 PM CET 
//


package eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sun.java.xml.ns.javaee package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _WebAppDescription_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "description");
    private final static QName _WebAppDisplayName_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "display-name");
    private final static QName _WebAppIcon_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "icon");
    private final static QName _WebAppDistributable_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "distributable");
    private final static QName _WebAppContextParam_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "context-param");
    private final static QName _WebAppFilter_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "filter");
    private final static QName _WebAppFilterMapping_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "filter-mapping");
    private final static QName _WebAppListener_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "listener");
    private final static QName _WebAppServlet_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "servlet");
    private final static QName _WebAppServletMapping_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "servlet-mapping");
    private final static QName _WebAppSessionConfig_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "session-config");
    private final static QName _WebAppMimeMapping_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "mime-mapping");
    private final static QName _WebAppWelcomeFileList_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "welcome-file-list");
    private final static QName _WebAppErrorPage_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "error-page");
    private final static QName _WebAppJspConfig_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "jsp-config");
    private final static QName _WebAppSecurityConstraint_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "security-constraint");
    private final static QName _WebAppLoginConfig_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "login-config");
    private final static QName _WebAppSecurityRole_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "security-role");
    private final static QName _WebAppEnvEntry_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "env-entry");
    private final static QName _WebAppEjbRef_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "ejb-ref");
    private final static QName _WebAppEjbLocalRef_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "ejb-local-ref");
    private final static QName _WebAppServiceRef_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "service-ref");
    private final static QName _WebAppResourceRef_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "resource-ref");
    private final static QName _WebAppResourceEnvRef_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "resource-env-ref");
    private final static QName _WebAppMessageDestinationRef_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "message-destination-ref");
    private final static QName _WebAppPersistenceContextRef_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "persistence-context-ref");
    private final static QName _WebAppPersistenceUnitRef_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "persistence-unit-ref");
    private final static QName _WebAppPostConstruct_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "post-construct");
    private final static QName _WebAppPreDestroy_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "pre-destroy");
    private final static QName _WebAppMessageDestination_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "message-destination");
    private final static QName _WebAppLocaleEncodingMappingList_QNAME = new QName("http://java.sun.com/xml/ns/javaee", "locale-encoding-mapping-list");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sun.java.xml.ns.javaee
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WebXml }
     * 
     */
    public WebXml createWebApp() {
        return new WebXml();
    }

    /**
     * Create an instance of {@link DescriptionType }
     * 
     */
    public DescriptionType createDescriptionType() {
        return new DescriptionType();
    }

    /**
     * Create an instance of {@link DisplayNameType }
     * 
     */
    public DisplayNameType createDisplayNameType() {
        return new DisplayNameType();
    }

    /**
     * Create an instance of {@link IconType }
     * 
     */
    public IconType createIconType() {
        return new IconType();
    }

    /**
     * Create an instance of {@link EmptyType }
     * 
     */
    public EmptyType createEmptyType() {
        return new EmptyType();
    }

    /**
     * Create an instance of {@link ParamValueType }
     * 
     */
    public ParamValueType createParamValueType() {
        return new ParamValueType();
    }

    /**
     * Create an instance of {@link FilterType }
     * 
     */
    public FilterType createFilterType() {
        return new FilterType();
    }

    /**
     * Create an instance of {@link FilterMappingType }
     * 
     */
    public FilterMappingType createFilterMappingType() {
        return new FilterMappingType();
    }

    /**
     * Create an instance of {@link ListenerType }
     * 
     */
    public ListenerType createListenerType() {
        return new ListenerType();
    }

    /**
     * Create an instance of {@link ServletType }
     * 
     */
    public ServletType createServletType() {
        return new ServletType();
    }

    /**
     * Create an instance of {@link ServletMappingType }
     * 
     */
    public ServletMappingType createServletMappingType() {
        return new ServletMappingType();
    }

    /**
     * Create an instance of {@link SessionConfigType }
     * 
     */
    public SessionConfigType createSessionConfigType() {
        return new SessionConfigType();
    }

    /**
     * Create an instance of {@link MimeMappingType }
     * 
     */
    public MimeMappingType createMimeMappingType() {
        return new MimeMappingType();
    }

    /**
     * Create an instance of {@link WelcomeFileListType }
     * 
     */
    public WelcomeFileListType createWelcomeFileListType() {
        return new WelcomeFileListType();
    }

    /**
     * Create an instance of {@link ErrorPageType }
     * 
     */
    public ErrorPageType createErrorPageType() {
        return new ErrorPageType();
    }

    /**
     * Create an instance of {@link JspConfigType }
     * 
     */
    public JspConfigType createJspConfigType() {
        return new JspConfigType();
    }

    /**
     * Create an instance of {@link SecurityConstraintType }
     * 
     */
    public SecurityConstraintType createSecurityConstraintType() {
        return new SecurityConstraintType();
    }

    /**
     * Create an instance of {@link LoginConfigType }
     * 
     */
    public LoginConfigType createLoginConfigType() {
        return new LoginConfigType();
    }

    /**
     * Create an instance of {@link SecurityRoleType }
     * 
     */
    public SecurityRoleType createSecurityRoleType() {
        return new SecurityRoleType();
    }

    /**
     * Create an instance of {@link EnvEntryType }
     * 
     */
    public EnvEntryType createEnvEntryType() {
        return new EnvEntryType();
    }

    /**
     * Create an instance of {@link EjbRefType }
     * 
     */
    public EjbRefType createEjbRefType() {
        return new EjbRefType();
    }

    /**
     * Create an instance of {@link EjbLocalRefType }
     * 
     */
    public EjbLocalRefType createEjbLocalRefType() {
        return new EjbLocalRefType();
    }

    /**
     * Create an instance of {@link ServiceRefType }
     * 
     */
    public ServiceRefType createServiceRefType() {
        return new ServiceRefType();
    }

    /**
     * Create an instance of {@link ResourceRefType }
     * 
     */
    public ResourceRefType createResourceRefType() {
        return new ResourceRefType();
    }

    /**
     * Create an instance of {@link ResourceEnvRefType }
     * 
     */
    public ResourceEnvRefType createResourceEnvRefType() {
        return new ResourceEnvRefType();
    }

    /**
     * Create an instance of {@link MessageDestinationRefType }
     * 
     */
    public MessageDestinationRefType createMessageDestinationRefType() {
        return new MessageDestinationRefType();
    }

    /**
     * Create an instance of {@link PersistenceContextRefType }
     * 
     */
    public PersistenceContextRefType createPersistenceContextRefType() {
        return new PersistenceContextRefType();
    }

    /**
     * Create an instance of {@link PersistenceUnitRefType }
     * 
     */
    public PersistenceUnitRefType createPersistenceUnitRefType() {
        return new PersistenceUnitRefType();
    }

    /**
     * Create an instance of {@link LifecycleCallbackType }
     * 
     */
    public LifecycleCallbackType createLifecycleCallbackType() {
        return new LifecycleCallbackType();
    }

    /**
     * Create an instance of {@link MessageDestinationType }
     * 
     */
    public MessageDestinationType createMessageDestinationType() {
        return new MessageDestinationType();
    }

    /**
     * Create an instance of {@link LocaleEncodingMappingListType }
     * 
     */
    public LocaleEncodingMappingListType createLocaleEncodingMappingListType() {
        return new LocaleEncodingMappingListType();
    }

    /**
     * Create an instance of {@link PortComponentRefType }
     * 
     */
    public PortComponentRefType createPortComponentRefType() {
        return new PortComponentRefType();
    }

    /**
     * Create an instance of {@link ServiceRefHandlerChainType }
     * 
     */
    public ServiceRefHandlerChainType createServiceRefHandlerChainType() {
        return new ServiceRefHandlerChainType();
    }

    /**
     * Create an instance of {@link ServiceRefHandlerChainsType }
     * 
     */
    public ServiceRefHandlerChainsType createServiceRefHandlerChainsType() {
        return new ServiceRefHandlerChainsType();
    }

    /**
     * Create an instance of {@link ServiceRefHandlerType }
     * 
     */
    public ServiceRefHandlerType createServiceRefHandlerType() {
        return new ServiceRefHandlerType();
    }

    /**
     * Create an instance of {@link EjbLinkType }
     * 
     */
    public EjbLinkType createEjbLinkType() {
        return new EjbLinkType();
    }

    /**
     * Create an instance of {@link EjbRefNameType }
     * 
     */
    public EjbRefNameType createEjbRefNameType() {
        return new EjbRefNameType();
    }

    /**
     * Create an instance of {@link EjbRefTypeType }
     * 
     */
    public EjbRefTypeType createEjbRefTypeType() {
        return new EjbRefTypeType();
    }

    /**
     * Create an instance of {@link EnvEntryTypeValuesType }
     * 
     */
    public EnvEntryTypeValuesType createEnvEntryTypeValuesType() {
        return new EnvEntryTypeValuesType();
    }

    /**
     * Create an instance of {@link FullyQualifiedClassType }
     * 
     */
    public FullyQualifiedClassType createFullyQualifiedClassType() {
        return new FullyQualifiedClassType();
    }

    /**
     * Create an instance of {@link GenericBooleanType }
     * 
     */
    public GenericBooleanType createGenericBooleanType() {
        return new GenericBooleanType();
    }

    /**
     * Create an instance of {@link HomeType }
     * 
     */
    public HomeType createHomeType() {
        return new HomeType();
    }

    /**
     * Create an instance of {@link InjectionTargetType }
     * 
     */
    public InjectionTargetType createInjectionTargetType() {
        return new InjectionTargetType();
    }

    /**
     * Create an instance of {@link JavaIdentifierType }
     * 
     */
    public JavaIdentifierType createJavaIdentifierType() {
        return new JavaIdentifierType();
    }

    /**
     * Create an instance of {@link JavaTypeType }
     * 
     */
    public JavaTypeType createJavaTypeType() {
        return new JavaTypeType();
    }

    /**
     * Create an instance of {@link JndiNameType }
     * 
     */
    public JndiNameType createJndiNameType() {
        return new JndiNameType();
    }

    /**
     * Create an instance of {@link LocalHomeType }
     * 
     */
    public LocalHomeType createLocalHomeType() {
        return new LocalHomeType();
    }

    /**
     * Create an instance of {@link LocalType }
     * 
     */
    public LocalType createLocalType() {
        return new LocalType();
    }

    /**
     * Create an instance of {@link MessageDestinationLinkType }
     * 
     */
    public MessageDestinationLinkType createMessageDestinationLinkType() {
        return new MessageDestinationLinkType();
    }

    /**
     * Create an instance of {@link MessageDestinationTypeType }
     * 
     */
    public MessageDestinationTypeType createMessageDestinationTypeType() {
        return new MessageDestinationTypeType();
    }

    /**
     * Create an instance of {@link MessageDestinationUsageType }
     * 
     */
    public MessageDestinationUsageType createMessageDestinationUsageType() {
        return new MessageDestinationUsageType();
    }

    /**
     * Create an instance of {@link PathType }
     * 
     */
    public PathType createPathType() {
        return new PathType();
    }

    /**
     * Create an instance of {@link PersistenceContextTypeType }
     * 
     */
    public PersistenceContextTypeType createPersistenceContextTypeType() {
        return new PersistenceContextTypeType();
    }

    /**
     * Create an instance of {@link PropertyType }
     * 
     */
    public PropertyType createPropertyType() {
        return new PropertyType();
    }

    /**
     * Create an instance of {@link RemoteType }
     * 
     */
    public RemoteType createRemoteType() {
        return new RemoteType();
    }

    /**
     * Create an instance of {@link ResAuthType }
     * 
     */
    public ResAuthType createResAuthType() {
        return new ResAuthType();
    }

    /**
     * Create an instance of {@link ResSharingScopeType }
     * 
     */
    public ResSharingScopeType createResSharingScopeType() {
        return new ResSharingScopeType();
    }

    /**
     * Create an instance of {@link RoleNameType }
     * 
     */
    public RoleNameType createRoleNameType() {
        return new RoleNameType();
    }

    /**
     * Create an instance of {@link RunAsType }
     * 
     */
    public RunAsType createRunAsType() {
        return new RunAsType();
    }

    /**
     * Create an instance of {@link SecurityRoleRefType }
     * 
     */
    public SecurityRoleRefType createSecurityRoleRefType() {
        return new SecurityRoleRefType();
    }

    /**
     * Create an instance of {@link String }
     * 
     */
    public String createString() {
        return new String();
    }

    /**
     * Create an instance of {@link TrueFalseType }
     * 
     */
    public TrueFalseType createTrueFalseType() {
        return new TrueFalseType();
    }

    /**
     * Create an instance of {@link UrlPatternType }
     * 
     */
    public UrlPatternType createUrlPatternType() {
        return new UrlPatternType();
    }

    /**
     * Create an instance of {@link XsdAnyURIType }
     * 
     */
    public XsdAnyURIType createXsdAnyURIType() {
        return new XsdAnyURIType();
    }

    /**
     * Create an instance of {@link XsdBooleanType }
     * 
     */
    public XsdBooleanType createXsdBooleanType() {
        return new XsdBooleanType();
    }

    /**
     * Create an instance of {@link XsdIntegerType }
     * 
     */
    public XsdIntegerType createXsdIntegerType() {
        return new XsdIntegerType();
    }

    /**
     * Create an instance of {@link XsdNMTOKENType }
     * 
     */
    public XsdNMTOKENType createXsdNMTOKENType() {
        return new XsdNMTOKENType();
    }

    /**
     * Create an instance of {@link XsdNonNegativeIntegerType }
     * 
     */
    public XsdNonNegativeIntegerType createXsdNonNegativeIntegerType() {
        return new XsdNonNegativeIntegerType();
    }

    /**
     * Create an instance of {@link XsdPositiveIntegerType }
     * 
     */
    public XsdPositiveIntegerType createXsdPositiveIntegerType() {
        return new XsdPositiveIntegerType();
    }

    /**
     * Create an instance of {@link XsdQNameType }
     * 
     */
    public XsdQNameType createXsdQNameType() {
        return new XsdQNameType();
    }

    /**
     * Create an instance of {@link XsdStringType }
     * 
     */
    public XsdStringType createXsdStringType() {
        return new XsdStringType();
    }

    /**
     * Create an instance of {@link JspFileType }
     * 
     */
    public JspFileType createJspFileType() {
        return new JspFileType();
    }

    /**
     * Create an instance of {@link JspPropertyGroupType }
     * 
     */
    public JspPropertyGroupType createJspPropertyGroupType() {
        return new JspPropertyGroupType();
    }

    /**
     * Create an instance of {@link TaglibType }
     * 
     */
    public TaglibType createTaglibType() {
        return new TaglibType();
    }

    /**
     * Create an instance of {@link AuthConstraintType }
     * 
     */
    public AuthConstraintType createAuthConstraintType() {
        return new AuthConstraintType();
    }

    /**
     * Create an instance of {@link AuthMethodType }
     * 
     */
    public AuthMethodType createAuthMethodType() {
        return new AuthMethodType();
    }

    /**
     * Create an instance of {@link DispatcherType }
     * 
     */
    public DispatcherType createDispatcherType() {
        return new DispatcherType();
    }

    /**
     * Create an instance of {@link ErrorCodeType }
     * 
     */
    public ErrorCodeType createErrorCodeType() {
        return new ErrorCodeType();
    }

    /**
     * Create an instance of {@link FilterNameType }
     * 
     */
    public FilterNameType createFilterNameType() {
        return new FilterNameType();
    }

    /**
     * Create an instance of {@link FormLoginConfigType }
     * 
     */
    public FormLoginConfigType createFormLoginConfigType() {
        return new FormLoginConfigType();
    }

    /**
     * Create an instance of {@link LocaleEncodingMappingType }
     * 
     */
    public LocaleEncodingMappingType createLocaleEncodingMappingType() {
        return new LocaleEncodingMappingType();
    }

    /**
     * Create an instance of {@link MimeTypeType }
     * 
     */
    public MimeTypeType createMimeTypeType() {
        return new MimeTypeType();
    }

    /**
     * Create an instance of {@link NonEmptyStringType }
     * 
     */
    public NonEmptyStringType createNonEmptyStringType() {
        return new NonEmptyStringType();
    }

    /**
     * Create an instance of {@link ServletNameType }
     * 
     */
    public ServletNameType createServletNameType() {
        return new ServletNameType();
    }

    /**
     * Create an instance of {@link TransportGuaranteeType }
     * 
     */
    public TransportGuaranteeType createTransportGuaranteeType() {
        return new TransportGuaranteeType();
    }

    /**
     * Create an instance of {@link UserDataConstraintType }
     * 
     */
    public UserDataConstraintType createUserDataConstraintType() {
        return new UserDataConstraintType();
    }

    /**
     * Create an instance of {@link WarPathType }
     * 
     */
    public WarPathType createWarPathType() {
        return new WarPathType();
    }

    /**
     * Create an instance of {@link WebResourceCollectionType }
     * 
     */
    public WebResourceCollectionType createWebResourceCollectionType() {
        return new WebResourceCollectionType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DescriptionType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DescriptionType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "description", scope = WebXml.class)
    public JAXBElement<DescriptionType> createWebAppDescription(DescriptionType value) {
        return new JAXBElement<DescriptionType>(_WebAppDescription_QNAME, DescriptionType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DisplayNameType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DisplayNameType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "display-name", scope = WebXml.class)
    public JAXBElement<DisplayNameType> createWebAppDisplayName(DisplayNameType value) {
        return new JAXBElement<DisplayNameType>(_WebAppDisplayName_QNAME, DisplayNameType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IconType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link IconType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "icon", scope = WebXml.class)
    public JAXBElement<IconType> createWebAppIcon(IconType value) {
        return new JAXBElement<IconType>(_WebAppIcon_QNAME, IconType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EmptyType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "distributable", scope = WebXml.class)
    public JAXBElement<EmptyType> createWebAppDistributable(EmptyType value) {
        return new JAXBElement<EmptyType>(_WebAppDistributable_QNAME, EmptyType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParamValueType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ParamValueType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "context-param", scope = WebXml.class)
    public JAXBElement<ParamValueType> createWebAppContextParam(ParamValueType value) {
        return new JAXBElement<ParamValueType>(_WebAppContextParam_QNAME, ParamValueType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FilterType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FilterType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "filter", scope = WebXml.class)
    public JAXBElement<FilterType> createWebAppFilter(FilterType value) {
        return new JAXBElement<FilterType>(_WebAppFilter_QNAME, FilterType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FilterMappingType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FilterMappingType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "filter-mapping", scope = WebXml.class)
    public JAXBElement<FilterMappingType> createWebAppFilterMapping(FilterMappingType value) {
        return new JAXBElement<FilterMappingType>(_WebAppFilterMapping_QNAME, FilterMappingType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListenerType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ListenerType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "listener", scope = WebXml.class)
    public JAXBElement<ListenerType> createWebAppListener(ListenerType value) {
        return new JAXBElement<ListenerType>(_WebAppListener_QNAME, ListenerType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServletType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ServletType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "servlet", scope = WebXml.class)
    public JAXBElement<ServletType> createWebAppServlet(ServletType value) {
        return new JAXBElement<ServletType>(_WebAppServlet_QNAME, ServletType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServletMappingType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ServletMappingType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "servlet-mapping", scope = WebXml.class)
    public JAXBElement<ServletMappingType> createWebAppServletMapping(ServletMappingType value) {
        return new JAXBElement<ServletMappingType>(_WebAppServletMapping_QNAME, ServletMappingType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SessionConfigType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SessionConfigType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "session-config", scope = WebXml.class)
    public JAXBElement<SessionConfigType> createWebAppSessionConfig(SessionConfigType value) {
        return new JAXBElement<SessionConfigType>(_WebAppSessionConfig_QNAME, SessionConfigType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MimeMappingType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MimeMappingType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "mime-mapping", scope = WebXml.class)
    public JAXBElement<MimeMappingType> createWebAppMimeMapping(MimeMappingType value) {
        return new JAXBElement<MimeMappingType>(_WebAppMimeMapping_QNAME, MimeMappingType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WelcomeFileListType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link WelcomeFileListType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "welcome-file-list", scope = WebXml.class)
    public JAXBElement<WelcomeFileListType> createWebAppWelcomeFileList(WelcomeFileListType value) {
        return new JAXBElement<WelcomeFileListType>(_WebAppWelcomeFileList_QNAME, WelcomeFileListType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorPageType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ErrorPageType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "error-page", scope = WebXml.class)
    public JAXBElement<ErrorPageType> createWebAppErrorPage(ErrorPageType value) {
        return new JAXBElement<ErrorPageType>(_WebAppErrorPage_QNAME, ErrorPageType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link JspConfigType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link JspConfigType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "jsp-config", scope = WebXml.class)
    public JAXBElement<JspConfigType> createWebAppJspConfig(JspConfigType value) {
        return new JAXBElement<JspConfigType>(_WebAppJspConfig_QNAME, JspConfigType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SecurityConstraintType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SecurityConstraintType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "security-constraint", scope = WebXml.class)
    public JAXBElement<SecurityConstraintType> createWebAppSecurityConstraint(SecurityConstraintType value) {
        return new JAXBElement<SecurityConstraintType>(_WebAppSecurityConstraint_QNAME, SecurityConstraintType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginConfigType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LoginConfigType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "login-config", scope = WebXml.class)
    public JAXBElement<LoginConfigType> createWebAppLoginConfig(LoginConfigType value) {
        return new JAXBElement<LoginConfigType>(_WebAppLoginConfig_QNAME, LoginConfigType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SecurityRoleType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SecurityRoleType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "security-role", scope = WebXml.class)
    public JAXBElement<SecurityRoleType> createWebAppSecurityRole(SecurityRoleType value) {
        return new JAXBElement<SecurityRoleType>(_WebAppSecurityRole_QNAME, SecurityRoleType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnvEntryType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EnvEntryType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "env-entry", scope = WebXml.class)
    public JAXBElement<EnvEntryType> createWebAppEnvEntry(EnvEntryType value) {
        return new JAXBElement<EnvEntryType>(_WebAppEnvEntry_QNAME, EnvEntryType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EjbRefType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EjbRefType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "ejb-ref", scope = WebXml.class)
    public JAXBElement<EjbRefType> createWebAppEjbRef(EjbRefType value) {
        return new JAXBElement<EjbRefType>(_WebAppEjbRef_QNAME, EjbRefType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EjbLocalRefType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EjbLocalRefType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "ejb-local-ref", scope = WebXml.class)
    public JAXBElement<EjbLocalRefType> createWebAppEjbLocalRef(EjbLocalRefType value) {
        return new JAXBElement<EjbLocalRefType>(_WebAppEjbLocalRef_QNAME, EjbLocalRefType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceRefType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ServiceRefType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "service-ref", scope = WebXml.class)
    public JAXBElement<ServiceRefType> createWebAppServiceRef(ServiceRefType value) {
        return new JAXBElement<ServiceRefType>(_WebAppServiceRef_QNAME, ServiceRefType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResourceRefType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ResourceRefType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "resource-ref", scope = WebXml.class)
    public JAXBElement<ResourceRefType> createWebAppResourceRef(ResourceRefType value) {
        return new JAXBElement<ResourceRefType>(_WebAppResourceRef_QNAME, ResourceRefType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResourceEnvRefType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ResourceEnvRefType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "resource-env-ref", scope = WebXml.class)
    public JAXBElement<ResourceEnvRefType> createWebAppResourceEnvRef(ResourceEnvRefType value) {
        return new JAXBElement<ResourceEnvRefType>(_WebAppResourceEnvRef_QNAME, ResourceEnvRefType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MessageDestinationRefType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MessageDestinationRefType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "message-destination-ref", scope = WebXml.class)
    public JAXBElement<MessageDestinationRefType> createWebAppMessageDestinationRef(MessageDestinationRefType value) {
        return new JAXBElement<MessageDestinationRefType>(_WebAppMessageDestinationRef_QNAME, MessageDestinationRefType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersistenceContextRefType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PersistenceContextRefType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "persistence-context-ref", scope = WebXml.class)
    public JAXBElement<PersistenceContextRefType> createWebAppPersistenceContextRef(PersistenceContextRefType value) {
        return new JAXBElement<PersistenceContextRefType>(_WebAppPersistenceContextRef_QNAME, PersistenceContextRefType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersistenceUnitRefType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PersistenceUnitRefType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "persistence-unit-ref", scope = WebXml.class)
    public JAXBElement<PersistenceUnitRefType> createWebAppPersistenceUnitRef(PersistenceUnitRefType value) {
        return new JAXBElement<PersistenceUnitRefType>(_WebAppPersistenceUnitRef_QNAME, PersistenceUnitRefType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LifecycleCallbackType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LifecycleCallbackType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "post-construct", scope = WebXml.class)
    public JAXBElement<LifecycleCallbackType> createWebAppPostConstruct(LifecycleCallbackType value) {
        return new JAXBElement<LifecycleCallbackType>(_WebAppPostConstruct_QNAME, LifecycleCallbackType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LifecycleCallbackType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LifecycleCallbackType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "pre-destroy", scope = WebXml.class)
    public JAXBElement<LifecycleCallbackType> createWebAppPreDestroy(LifecycleCallbackType value) {
        return new JAXBElement<LifecycleCallbackType>(_WebAppPreDestroy_QNAME, LifecycleCallbackType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MessageDestinationType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MessageDestinationType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "message-destination", scope = WebXml.class)
    public JAXBElement<MessageDestinationType> createWebAppMessageDestination(MessageDestinationType value) {
        return new JAXBElement<MessageDestinationType>(_WebAppMessageDestination_QNAME, MessageDestinationType.class, WebXml.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LocaleEncodingMappingListType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LocaleEncodingMappingListType }{@code >}
     */
    @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/javaee", name = "locale-encoding-mapping-list", scope = WebXml.class)
    public JAXBElement<LocaleEncodingMappingListType> createWebAppLocaleEncodingMappingList(LocaleEncodingMappingListType value) {
        return new JAXBElement<LocaleEncodingMappingListType>(_WebAppLocaleEncodingMappingList_QNAME, LocaleEncodingMappingListType.class, WebXml.class, value);
    }

}
