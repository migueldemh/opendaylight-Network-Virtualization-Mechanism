package org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105;
import org.opendaylight.yangtools.yang.binding.RpcService;
import org.opendaylight.yangtools.yang.common.RpcResult;
import java.util.concurrent.Future;


/**
 * Interface for implementing the following YANG RPCs defined in module &lt;b&gt;dtu&lt;/b&gt;
 * &lt;br&gt;(Source path: &lt;i&gt;META-INF/yang/dtu.yang&lt;/i&gt;):
 * &lt;pre&gt;
 * rpc add-host-virtual-network {
 *     input {
 *         leaf IP {
 *             type string;
 *         }
 *         leaf Virtual-Network {
 *             type string;
 *         }
 *     }
 *     
 *     output {
 *         leaf Tunnels {
 *             type string;
 *         }
 *     }
 *     status CURRENT;
 * }
 * &lt;/pre&gt;
 *
 */
public interface DtuService
    extends
    RpcService
{




    Future<RpcResult<AddHostVirtualNetworkOutput>> addHostVirtualNetwork(AddHostVirtualNetworkInput input);

}

