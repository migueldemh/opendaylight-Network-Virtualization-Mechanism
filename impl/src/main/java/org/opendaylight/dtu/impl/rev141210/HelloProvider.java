package org.opendaylight.dtu.impl.rev141210;

import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.ProviderContext;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.RpcRegistration;
import org.opendaylight.controller.sal.binding.api.BindingAwareProvider;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.DtuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloProvider implements  AutoCloseable,BindingAwareProvider {

    private static final Logger LOG = LoggerFactory.getLogger(HelloProvider.class);
    private RpcRegistration<DtuService> helloService;

    @Override
    public void onSessionInitiated(ProviderContext session) {
        LOG.info("DTUProvider Session Initiated");
        helloService = session.addRpcImplementation(DtuService.class, new AddHostRest());
    }

    @Override
    public void close() throws Exception {
        LOG.info("HelloProvider Closed");
        if (helloService != null) {
            helloService.close();
        }
    }

}
