CREATE TABLE transaction_category (
    id UUID PRIMARY KEY,
    description varchar(100) DEFAULT NULL,
    name varchar(30) DEFAULT NULL,
    id_user UUID NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by UUID,
    updated_by UUID
)