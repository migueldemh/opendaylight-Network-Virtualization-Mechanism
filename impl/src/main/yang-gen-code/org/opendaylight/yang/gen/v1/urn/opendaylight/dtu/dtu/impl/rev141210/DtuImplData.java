package org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210;
import org.opendaylight.yangtools.yang.binding.DataRoot;


/**
 * Service definition for dtu project
 *
 * &lt;p&gt;This class represents the following YANG schema fragment defined in module &lt;b&gt;dtu-impl&lt;/b&gt;
 * &lt;br&gt;Source path: &lt;i&gt;META-INF/yang/dtu-impl.yang&lt;/i&gt;):
 * &lt;pre&gt;
 * module dtu-impl {
 *     yang-version 1;
 *     namespace "urn:opendaylight:dtu:dtu-impl";
 *     prefix "dtu-impl";
 *
 *     import opendaylight-md-sal-binding { prefix "md-sal-binding"; }
 *     
 *     import config { prefix "config"; }
 *     revision 2014-12-10 {
 *         description "Service definition for dtu project
 *         ";
 *     }
 *
 *     container broker {
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
 *
 *     augment \(urn:opendaylight:params:xml:ns:yang:controller:config)modules\(urn:opendaylight:params:xml:ns:yang:controller:config)module\(urn:opendaylight:params:xml:ns:yang:controller:config)configuration {
 *         status CURRENT;
 *         case dtu-impl {
 *             container data-broker {
 *                 leaf type {
 *                     type leafref;
 *                 }
 *                 leaf name {
 *                     type leafref;
 *                 }
 *                 uses service-ref {
 *                     refine (urn:opendaylight:dtu:dtu-impl?revision=2014-12-10)type {
 *                         leaf type {
 *                             type leafref;
 *                         }
 *                     }
 *                 }
 *             }
 *             container notification-service {
 *                 leaf type {
 *                     type leafref;
 *                 }
 *                 leaf name {
 *                     type leafref;
 *                 }
 *                 uses service-ref {
 *                     refine (urn:opendaylight:dtu:dtu-impl?revision=2014-12-10)type {
 *                         leaf type {
 *                             type leafref;
 *                         }
 *                     }
 *                 }
 *             }
 *             container rpc-registry {
 *                 leaf type {
 *                     type leafref;
 *                 }
 *                 leaf name {
 *                     type leafref;
 *                 }
 *                 uses service-ref {
 *                     refine (urn:opendaylight:dtu:dtu-impl?revision=2014-12-10)type {
 *                         leaf type {
 *                             type leafref;
 *                         }
 *                     }
 *                 }
 *             }
 *         }
 *     }
 *
 *     identity dtu-impl {
 *         base "()IdentitySchemaNodeImpl[base=null, qname=(urn:opendaylight:params:xml:ns:yang:controller:config?revision=2013-04-05)module-type]";
 *         status CURRENT;
 *     }
 * }
 * &lt;/pre&gt;
 *
 */
public interface DtuImplData
    extends
    DataRoot
{




    Broker getBroker();

}

