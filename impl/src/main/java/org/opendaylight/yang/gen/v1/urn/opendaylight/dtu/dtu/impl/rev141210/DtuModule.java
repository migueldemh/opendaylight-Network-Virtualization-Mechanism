package org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;

import org.opendaylight.dtu.impl.rev141210.HelloProvider;
import org.opendaylight.dtu.impl.rev141210.DtuMain;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.DtuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DtuModule extends org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.AbstractDtuModule {
    BindingAwareBroker.RpcRegistration<DtuService> rpcRegistration;
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    public DtuModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public DtuModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.DtuModule oldModule, java.lang.AutoCloseable oldInstance) {
        super(identifier, dependencyResolver, oldModule, oldInstance);
    }

    @Override
    public void customValidation() {
        // add custom validation form module attributes here.
    }

    @Override
    public java.lang.AutoCloseable createInstance() {

        DataBroker dataBroker = getDataBrokerDependency();
        RpcProviderRegistry rpcRegistry = getRpcRegistryDependency();
        NotificationProviderService notificationService = getNotificationServiceDependency();


//        LOG.info("???????????????????????????0????????????????????????????");

       final HelloProvider provider=new HelloProvider();
//        LOG.info("???????????????????????????1????????????????????????????");

//        LOG.info("???????????????????????????2????????????????????????????");


      getBrokerDependency().registerProvider(provider);
       DtuMain dtuMain = new DtuMain(dataBroker, notificationService, rpcRegistry);


//        LOG.info("???????????????????????????2????????????????????????????");

       dtuMain.init();



        return new AutoCloseable() {
            @Override
            public void close() throws Exception {
                provider.close();
            }
        };

    }
    }


