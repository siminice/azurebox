package org.vbossica.azurebox.servicebus;

import com.microsoft.windowsazure.services.core.Configuration;
import com.microsoft.windowsazure.services.serviceBus.ServiceBusConfiguration;
import com.microsoft.windowsazure.services.serviceBus.ServiceBusContract;
import com.microsoft.windowsazure.services.serviceBus.ServiceBusService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;


/**
 * @author Bernd Kiefer (b.kiefer@raion.de)
 */
public class ServiceBusContractFactoryBean implements FactoryBean<ServiceBusContract>, InitializingBean {

    private static final String DEFAULT_SERVICEBUS_URI = ".servicebus.windows.net";
    private static final String DEFAULT_WRAP_URI = "-sb.accesscontrol.windows.net/WRAPv0.9";

    private String namespace;
    private String owner;
    private String key;
    private String serviceBusRootUri = DEFAULT_SERVICEBUS_URI;
    private String wrapRootUri = DEFAULT_WRAP_URI;


    @Override
    public ServiceBusContract getObject() throws Exception {
        Configuration config = ServiceBusConfiguration.configureWithWrapAuthentication(namespace,
                                                                                       owner,
                                                                                       key,
                                                                                       serviceBusRootUri,
                                                                                       wrapRootUri);
        return ServiceBusService.create(config);
    }

    @Override
    public Class<?> getObjectType() {
        return ServiceBusContract.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(namespace, "namespace must be set!");
        Assert.isTrue((!namespace.equals("")), "no empty string for namespace allowed");
        Assert.notNull(owner, "owner must be set!");
        Assert.notNull(key, "key must be set!");
    }

    public void setNamespace(String aNamespace) {
        namespace = aNamespace;
    }

    public String getNamespace() {
       return namespace;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getServiceBusRootUri() {
        return serviceBusRootUri;
    }

    public void setServiceBusRootUri(String serviceBusRootUri) {
        this.serviceBusRootUri = serviceBusRootUri;
    }

    public String getWrapRootUri() {
        return wrapRootUri;
    }

    public void setWrapRootUri(String wrapRootUri) {
        this.wrapRootUri = wrapRootUri;
    }
}
