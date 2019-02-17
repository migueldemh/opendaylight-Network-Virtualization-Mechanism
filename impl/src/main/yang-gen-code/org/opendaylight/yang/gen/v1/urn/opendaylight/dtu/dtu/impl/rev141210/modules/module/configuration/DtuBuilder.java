package org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.dtu.Broker;
import org.opendaylight.yangtools.yang.binding.DataObject;
import java.util.HashMap;
import org.opendaylight.yangtools.concepts.Builder;
import java.util.Collections;
import java.util.Map;


/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu} instances.
 *
 * @see org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu
 *
 */
public class DtuBuilder implements Builder <org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu> {

    private Broker _broker;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu>> augmentation = Collections.emptyMap();

    public DtuBuilder() {
    }

    public DtuBuilder(Dtu base) {
        this._broker = base.getBroker();
        if (base instanceof DtuImpl) {
            DtuImpl impl = (DtuImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }


    public Broker getBroker() {
        return _broker;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

    public DtuBuilder setBroker(Broker value) {
        this._broker = value;
        return this;
    }
    
    public DtuBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu> augmentation) {
        if (augmentation == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentation);
        return this;
    }
    
    public DtuBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    public Dtu build() {
        return new DtuImpl(this);
    }

    private static final class DtuImpl implements Dtu {

        public java.lang.Class<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu.class;
        }

        private final Broker _broker;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu>> augmentation = Collections.emptyMap();

        private DtuImpl(DtuBuilder base) {
            this._broker = base.getBroker();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public Broker getBroker() {
            return _broker;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
            result = prime * result + ((_broker == null) ? 0 : _broker.hashCode());
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
            if (!org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu other = (org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu)obj;
            if (_broker == null) {
                if (other.getBroker() != null) {
                    return false;
                }
            } else if(!_broker.equals(other.getBroker())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                DtuImpl otherImpl = (DtuImpl) obj;
                if (augmentation == null) {
                    if (otherImpl.augmentation != null) {
                        return false;
                    }
                } else if(!augmentation.equals(otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.Dtu>> e : augmentation.entrySet()) {
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
            java.lang.StringBuilder builder = new java.lang.StringBuilder ("Dtu [");
            boolean first = true;
        
            if (_broker != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_broker=");
                builder.append(_broker);
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
