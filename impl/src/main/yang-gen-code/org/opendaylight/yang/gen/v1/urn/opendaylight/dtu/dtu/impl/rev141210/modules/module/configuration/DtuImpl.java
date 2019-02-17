package org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.dtu.impl.RpcRegistry;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.dtu.impl.DataBroker;
import org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.dtu.impl.NotificationService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.modules.module.Configuration;
import org.opendaylight.yangtools.yang.binding.Augmentable;


/**
 * &lt;p&gt;This class represents the following YANG schema fragment defined in module &lt;b&gt;dtu-impl&lt;/b&gt;
 * &lt;br&gt;(Source path: &lt;i&gt;META-INF/yang/dtu-impl.yang&lt;/i&gt;):
 * &lt;pre&gt;
 * case dtu-impl {
 *     container data-broker {
 *         leaf type {
 *             type leafref;
 *         }
 *         leaf name {
 *             type leafref;
 *         }
 *         uses service-ref {
 *             refine (urn:opendaylight:dtu:dtu-impl?revision=2014-12-10)type {
 *                 leaf type {
 *                     type leafref;
 *                 }
 *             }
 *         }
 *     }
 *     container notification-service {
 *         leaf type {
 *             type leafref;
 *         }
 *         leaf name {
 *             type leafref;
 *         }
 *         uses service-ref {
 *             refine (urn:opendaylight:dtu:dtu-impl?revision=2014-12-10)type {
 *                 leaf type {
 *                     type leafref;
 *                 }
 *             }
 *         }
 *     }
 *     container rpc-registry {
 *         leaf type {
 *             type leafref;
 *         }
 *         leaf name {
 *             type leafref;
 *         }
 *         uses service-ref {
 *             refine (urn:opendaylight:dtu:dtu-impl?revision=2014-12-10)type {
 *                 leaf type {
 *                     type leafref;
 *                 }
 *             }
 *         }
 *     }
 * }
 * &lt;/pre&gt;
 * The schema path to identify an instance is
 * &lt;i&gt;dtu-impl/modules/module/configuration/(urn:opendaylight:dtu:dtu-impl?revision=2014-12-10)dtu-impl&lt;/i&gt;
 *
 */
public interface DtuImpl
    extends
    DataObject,
    Augmentable<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl>,
    Configuration
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.cachedReference(org.opendaylight.yangtools.yang.common.QName.create("urn:opendaylight:dtu:dtu-impl","2014-12-10","dtu-impl"));

    DataBroker getDataBroker();
    
    NotificationService getNotificationService();
    
    RpcRegistry getRpcRegistry();

}

