package io.perfume.api.common.config.datasourece;

import static io.perfume.api.common.config.datasourece.DataSourceType.type.MASTER_NAME;
import static io.perfume.api.common.config.datasourece.DataSourceType.type.SLAVE_NAME;


public enum DataSourceType {

    MASTER(MASTER_NAME),
    SLAVE(SLAVE_NAME)
    ;

    private final String typeName;

    DataSourceType(String typeName) {
        this.typeName = typeName;
    }

    public static abstract class type {
        public static final String MASTER_NAME = "MASTER";
        public static final String SLAVE_NAME = "SLAVE";
        public static final String ROUTING_NAME = "ROUTING";
    }
}
