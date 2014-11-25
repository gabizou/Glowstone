package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.glowstone.entity.meta.MetadataMap;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.MetadataStore;
import org.bukkit.metadata.MetadataStoreBase;

@Data
@EqualsAndHashCode(callSuper = false)
public class MetadataComponent extends Component {

    /**
     * The entity's metadata.
     */
    protected final MetadataMap metadata;

    /**
     * The metadata store for entities.
     */
    public static final MetadataStore<Entity> bukkitMetadata = new EntityMetadataStore();

    public MetadataComponent(Class<? extends Entity> entityClass) {
        metadata = new MetadataMap(entityClass);
    }

    /**
     * The metadata store class for entities.
     */
    private static final class EntityMetadataStore extends MetadataStoreBase<Entity> implements MetadataStore<Entity> {

        @Override
        protected String disambiguate(Entity subject, String metadataKey) {
            return subject.getUniqueId() + ":" + metadataKey;
        }
    }
}
