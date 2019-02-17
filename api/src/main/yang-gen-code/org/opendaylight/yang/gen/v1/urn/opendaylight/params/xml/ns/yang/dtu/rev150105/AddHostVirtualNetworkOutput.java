package org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.binding.Augmentable;


/**
 * &lt;p&gt;This class represents the following YANG schema fragment defined in module &lt;b&gt;dtu&lt;/b&gt;
 * &lt;br&gt;(Source path: &lt;i&gt;META-INF/yang/dtu.yang&lt;/i&gt;):
 * &lt;pre&gt;
 * container output {
 *     leaf Tunnels {
 *         type string;
 *     }
 * }
 * &lt;/pre&gt;
 * The schema path to identify an instance is
 * &lt;i&gt;dtu/add-host-virtual-network/output&lt;/i&gt;
 *
 * &lt;p&gt;To create instances of this class use {@link org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutputBuilder}.
 * @see org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutputBuilder
 *
 */
public interface AddHostVirtualNetworkOutput
    extends
    DataObject,
    Augmentable<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput>
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.cachedReference(org.opendaylight.yangtools.yang.common.QName.create("urn:opendaylight:params:xml:ns:yang:dtu","2015-01-05","output"));

    java.lang.String getTunnels();

}

