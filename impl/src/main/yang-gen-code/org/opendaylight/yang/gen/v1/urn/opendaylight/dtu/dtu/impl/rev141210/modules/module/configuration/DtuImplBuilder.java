package org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration;
import java.util.Collections;
import java.util.Map;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import java.util.HashMap;
import org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.dtu.impl.RpcRegistry;
import org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.dtu.impl.DataBroker;
import org.opendaylight.yangtools.concepts.Builder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.dtu.impl.NotificationService;
import org.opendaylight.yangtools.yang.binding.Augmentation;


/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl} instances.
 *
 * @see org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl
 *
 */
public class DtuImplBuilder implements Builder <org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl> {

    private DataBroker _dataBroker;
    private NotificationService _notificationService;
    private RpcRegistry _rpcRegistry;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl>> augmentation = Collections.emptyMap();

    public DtuImplBuilder() {
    }

    public DtuImplBuilder(DtuImpl base) {
        this._dataBroker = base.getDataBroker();
        this._notificationService = base.getNotificationService();
        this._rpcRegistry = base.getRpcRegistry();
        if (base instanceof DtuImplImpl) {
            DtuImplImpl impl = (DtuImplImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }


    public DataBroker getDataBroker() {
        return _dataBroker;
    }
    
    public NotificationService getNotificationService() {
        return _notificationService;
    }
    
    public RpcRegistry getRpcRegistry() {
        return _rpcRegistry;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

    public DtuImplBuilder setDataBroker(DataBroker value) {
        this._dataBroker = value;
        return this;
    }
    
    public DtuImplBuilder setNotificationService(NotificationService value) {
        this._notificationService = value;
        return this;
    }
    
    public DtuImplBuilder setRpcRegistry(RpcRegistry value) {
        this._rpcRegistry = value;
        return this;
    }
    
    public DtuImplBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl> augmentation) {
        if (augmentation == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentation);
        return this;
    }
    
    public DtuImplBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    public DtuImpl build() {
        return new DtuImplImpl(this);
    }

    private static final class DtuImplImpl implements DtuImpl {

        public java.lang.Class<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl.class;
        }

        private final DataBroker _dataBroker;
        private final NotificationService _notificationService;
        private final RpcRegistry _rpcRegistry;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl>> augmentation = Collections.emptyMap();

        private DtuImplImpl(DtuImplBuilder base) {
            this._dataBroker = base.getDataBroker();
            this._notificationService = base.getNotificationService();
            this._rpcRegistry = base.getRpcRegistry();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public DataBroker getDataBroker() {
            return _dataBroker;
        }
        
        @Override
        public NotificationService getNotificationService() {
            return _notificationService;
        }
        
        @Override
        public RpcRegistry getRpcRegistry() {
            return _rpcRegistry;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
            result = prime * result + ((_dataBroker == null) ? 0 : _dataBroker.hashCode());
            result = prime * result + ((_notificationService == null) ? 0 : _notificationService.hashCode());
            result = prime * result + ((_rpcRegistry == null) ? 0 : _rpcRegistry.hashCode());
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
            if (!org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl other = (org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl)obj;
            if (_dataBroker == null) {
                if (other.getDataBroker() != null) {
                    return false;
                }
            } else if(!_dataBroker.equals(other.getDataBroker())) {
                return false;
            }
            if (_notificationService == null) {
                if (other.getNotificationService() != null) {
                    return false;
                }
            } else if(!_notificationService.equals(other.getNotificationService())) {
                return false;
            }
            if (_rpcRegistry == null) {
                if (other.getRpcRegistry() != null) {
                    return false;
                }
            } else if(!_rpcRegistry.equals(other.getRpcRegistry())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                DtuImplImpl otherImpl = (DtuImplImpl) obj;
                if (augmentation == null) {
                    if (otherImpl.augmentation != null) {
                        return false;
                    }
                } else if(!augmentation.equals(otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.DtuImpl>> e : augmentation.entrySet()) {
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
            java.lang.StringBuilder builder = new java.lang.StringBuilder ("DtuImpl [");
            boolean first = true;
        
            if (_dataBroker != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_dataBroker=");
                builder.append(_dataBroker);
             }
            if (_notificationService != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_notificationService=");
                builder.append(_notificationService);
             }
            if (_rpcRegistry != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_rpcRegistry=");
                builder.append(_rpcRegistry);
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
