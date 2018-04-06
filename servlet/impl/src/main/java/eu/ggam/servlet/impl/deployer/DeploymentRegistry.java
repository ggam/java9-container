package eu.ggam.servlet.impl.deployer;

import eu.ggam.servlet.impl.api.Deployment;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author guillermo
 */
public final class DeploymentRegistry {

    private static final Set<Deployment> DEPLOYMENTS = new HashSet<>();

    private static final ReadWriteLock lock = new ReentrantReadWriteLock();

    private static boolean deployed = false;

    private DeploymentRegistry() {
    }

    public static void registerDeployment(Deployment deployment) {
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            if (deployed) {
                throw new IllegalStateException("Applications were already deployed");
            }

            DEPLOYMENTS.add(deployment);
        } finally {
            writeLock.unlock();
        }
    }

    public static void deployAll() {
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            if (deployed) {
                throw new IllegalStateException("Applications were already deployed");
            }

            deployed = true;
            DEPLOYMENTS.forEach(Deployment::deploy);
        } finally {
            writeLock.unlock();
        }
    }

    public static void undeployAll() {
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            if (!deployed) {
                throw new IllegalStateException("Applications were already deployed");
            }

            deployed = true;
            DEPLOYMENTS.forEach(Deployment::undeploy);
        } finally {
            writeLock.unlock();
        }
    }

    public static void undeploy(String moduleName) {
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            // There will be only one
            Optional<Deployment> deployment = DEPLOYMENTS.stream().
                    filter(d -> d.getModuleName().equals(moduleName)).
                    findFirst();

            deployment.orElseThrow(() -> new IllegalArgumentException(moduleName + " not found")).
                    undeploy();

        } finally {
            writeLock.unlock();
        }
    }

    public static Optional<Deployment> matches(String uri) {
        Lock readLock = lock.readLock();
        readLock.lock();
        try {
            if (!deployed) {
                throw new IllegalStateException("Applications are still not deployed");
            }

            // Will return all deployments, even undeployed ones
            return DEPLOYMENTS.
                    stream().
                    filter(app -> app.matches(uri)).
                    max(Comparator.comparingInt((app) -> app.getContextPath().length()));
        } finally {
            readLock.unlock();
        }
    }

}
