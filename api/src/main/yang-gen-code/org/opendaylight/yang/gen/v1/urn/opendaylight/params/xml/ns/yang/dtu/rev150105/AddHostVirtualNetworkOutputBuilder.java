package org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yangtools.yang.binding.DataObject;
import java.util.HashMap;
import org.opendaylight.yangtools.concepts.Builder;
import java.util.Collections;
import java.util.Map;


/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput} instances.
 *
 * @see org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput
 *
 */
public class AddHostVirtualNetworkOutputBuilder implements Builder <org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput> {

    private java.lang.String _tunnels;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput>> augmentation = Collections.emptyMap();

    public AddHostVirtualNetworkOutputBuilder() {
    }

    public AddHostVirtualNetworkOutputBuilder(AddHostVirtualNetworkOutput base) {
        this._tunnels = base.getTunnels();
        if (base instanceof AddHostVirtualNetworkOutputImpl) {
            AddHostVirtualNetworkOutputImpl impl = (AddHostVirtualNetworkOutputImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }


    public java.lang.String getTunnels() {
        return _tunnels;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

    public AddHostVirtualNetworkOutputBuilder setTunnels(java.lang.String value) {
        this._tunnels = value;
        return this;
    }
    
    public AddHostVirtualNetworkOutputBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput> augmentation) {
        if (augmentation == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentation);
        return this;
    }
    
    public AddHostVirtualNetworkOutputBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    public AddHostVirtualNetworkOutput build() {
        return new AddHostVirtualNetworkOutputImpl(this);
    }

    private static final class AddHostVirtualNetworkOutputImpl implements AddHostVirtualNetworkOutput {

        public java.lang.Class<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput.class;
        }

        private final java.lang.String _tunnels;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput>> augmentation = Collections.emptyMap();

        private AddHostVirtualNetworkOutputImpl(AddHostVirtualNetworkOutputBuilder base) {
            this._tunnels = base.getTunnels();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public java.lang.String getTunnels() {
            return _tunnels;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput>> E getAugmentation(java.lang.Class<E> augmentationType) {
            if (augmentationType == null) {
                throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
            }
            return (E) augmentation.get(augmentationType);
        }

        private int hash = 0;
        private volatile boolean hashValid = false;
        
        @Override
        public int hashCode() {
            if (hashValid) {
                return hash;
            }
        
            final int prime = 31;
            int result = 1;
            result = prime * result + ((_tunnels == null) ? 0 : _tunnels.hashCode());
            result = prime * result + ((augmentation == null) ? 0 : augmentation.hashCode());
        
            hash = result;
            hashValid = true;
            return result;
        }

        @Override
        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DataObject)) {
                return false;
            }
            if (!org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput other = (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput)obj;
            if (_tunnels == null) {
                if (other.getTunnels() != null) {
                    return false;
                }
            } else if(!_tunnels.equals(other.getTunnels())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                AddHostVirtualNetworkOutputImpl otherImpl = (AddHostVirtualNetworkOutputImpl) obj;
                if (augmentation == null) {
                    if (otherImpl.augmentation != null) {
                        return false;
                    }
                } else if(!augmentation.equals(otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput>> e : augmentation.entrySet()) {
                    if (!e.getValue().equals(other.getAugmentation(e.getKey()))) {
                        return false;
                    }
                }
                // .. and give the other one the chance to do the same
                if (!obj.equals(this)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public java.lang.String toString() {
            java.lang.StringBuilder builder = new java.lang.StringBuilder ("AddHostVirtualNetworkOutput [");
            boolean first = true;
        
            if (_tunnels != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_tunnels=");
                builder.append(_tunnels);
             }
            if (first) {
                first = false;
            } else {
                builder.append(", ");
            }
            builder.append("augmentation=");
            builder.append(augmentation.values());
            return builder.append(']').toString();
        }
    }

}
