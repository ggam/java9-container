package eu.ggam.servlet.impl.deployer;

import eu.ggam.container.api.event.ServerLifeCycleListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author Guillermo González de Agüero
 */
public final class DeploymentManager implements ServerLifeCycleListener {

    private static final Path WEBAPPS_PATH = Paths.get("..", "webapps");
    private static final ReadWriteLock LOCK = new ReentrantReadWriteLock();

    private static ServletDeployment deployment;

    private DeploymentManager() {
    }

    public static void start() {
        Lock writeLock = LOCK.writeLock();
        writeLock.lock();
        try {
            if (DeploymentManager.deployment != null) {
                throw new IllegalStateException("Applications were already deployed");
            }

            DeploymentManager.deployment = new ServletDeployment.ServletDeploymentBuilder(ModuleLayer.boot(), WEBAPPS_PATH).
                    deploy();
        } finally {
            writeLock.unlock();
        }
    }

    public static void stop() {
        Lock writeLock = LOCK.writeLock();
        writeLock.lock();
        try {
            if (DeploymentManager.deployment == null) {
                throw new IllegalStateException("Applications were already deployed");
            }

            DeploymentManager.deployment = null; // No more requeres will be attended
            DeploymentManager.deployment.undeploy();
        } finally {
            writeLock.unlock();
        }
    }

    public static ServletDeployment getDeployment() {
        Lock readLock = LOCK.readLock();
        readLock.lock();
        try {
            if (DeploymentManager.deployment == null) {
                throw new IllegalStateException("Server is either still starting or stopping");
            }

            return DeploymentManager.deployment;
        } finally {
            readLock.unlock();
        }
    }

}
