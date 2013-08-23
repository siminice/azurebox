package org.vbossica.azurebox.table;


import com.microsoft.windowsazure.services.core.storage.CloudStorageAccount;
import com.microsoft.windowsazure.services.table.client.CloudTableClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;


/* creation date: 2013-08-23 1:53 PM *

/**
 *
 *
 * @author Bernd Kiefer <b.kiefer@raion.de>
 */
public class CloudTableClientFactoryBean implements FactoryBean<CloudTableClient>, InitializingBean {

    private CloudStorageAccount storageAccount;



    /**
     * Return an instance (possibly shared or independent) of the object
     * managed by this factory.
     * <p>As with a {@link org.springframework.beans.factory.BeanFactory}, this allows support for both the
     * Singleton and Prototype design pattern.
     * <p>If this FactoryBean is not fully initialized yet at the time of
     * the call (for example because it is involved in a circular reference),
     * throw a corresponding {@link org.springframework.beans.factory.FactoryBeanNotInitializedException}.
     * <p>As of Spring 2.0, FactoryBeans are allowed to return {@code null}
     * objects. The factory will consider this as normal value to be used; it
     * will not throw a FactoryBeanNotInitializedException in this case anymore.
     * FactoryBean implementations are encouraged to throw
     * FactoryBeanNotInitializedException themselves now, as appropriate.
     *
     * @return an instance of the bean (can be {@code null})
     * @throws Exception in case of creation errors
     * @see org.springframework.beans.factory.FactoryBeanNotInitializedException
     */
    @Override
    public CloudTableClient getObject() throws Exception {
        return storageAccount.createCloudTableClient();
    }

    /**
     * Return the type of object that this FactoryBean creates,
     * or {@code null} if not known in advance.
     * <p>This allows one to check for specific types of beans without
     * instantiating objects, for example on autowiring.
     * <p>In the case of implementations that are creating a singleton object,
     * this method should try to avoid singleton creation as far as possible;
     * it should rather estimate the type in advance.
     * For prototypes, returning a meaningful type here is advisable too.
     * <p>This method can be called <i>before</i> this FactoryBean has
     * been fully initialized. It must not rely on state created during
     * initialization; of course, it can still use such state if available.
     * <p><b>NOTE:</b> Autowiring will simply ignore FactoryBeans that return
     * {@code null} here. Therefore it is highly recommended to implement
     * this method properly, using the current state of the FactoryBean.
     *
     * @return the type of object that this FactoryBean creates,
     *         or {@code null} if not known at the time of the call
     * @see org.springframework.beans.factory.ListableBeanFactory#getBeansOfType
     */
    @Override
    public Class<?> getObjectType() {
        return CloudTableClient.class;
    }

    /**
     * Is the object managed by this factory a singleton? That is,
     * will {@link #getObject()} always return the same object
     * (a reference that can be cached)?
     * <p><b>NOTE:</b> If a FactoryBean indicates to hold a singleton object,
     * the object returned from {@code getObject()} might get cached
     * by the owning BeanFactory. Hence, do not return {@code true}
     * unless the FactoryBean always exposes the same reference.
     * <p>The singleton status of the FactoryBean itself will generally
     * be provided by the owning BeanFactory; usually, it has to be
     * defined as singleton there.
     * <p><b>NOTE:</b> This method returning {@code false} does not
     * necessarily indicate that returned objects are independent instances.
     * An implementation of the extended {@link org.springframework.beans.factory.SmartFactoryBean} interface
     * may explicitly indicate independent instances through its
     * {@link org.springframework.beans.factory.SmartFactoryBean#isPrototype()} method. Plain {@link org.springframework.beans.factory.FactoryBean}
     * implementations which do not implement this extended interface are
     * simply assumed to always return independent instances if the
     * {@code isSingleton()} implementation returns {@code false}.
     *
     * @return whether the exposed object is a singleton
     * @see #getObject()
     * @see org.springframework.beans.factory.SmartFactoryBean#isPrototype()
     */
    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * Invoked by a BeanFactory after it has set all bean properties supplied
     * (and satisfied BeanFactoryAware and ApplicationContextAware).
     * <p>This method allows the bean instance to perform initialization only
     * possible when all bean properties have been set and to throw an
     * exception in the event of misconfiguration.
     *
     * @throws Exception in the event of misconfiguration (such
     *                   as failure to set an essential property) or if initialization fails.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(storageAccount, "storageAccount must be set!");
    }

    public void setStorageAccount(CloudStorageAccount storageAccount) {
        this.storageAccount = storageAccount;
    }
}
