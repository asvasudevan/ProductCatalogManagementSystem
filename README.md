# ProductCatalogManagementSystem v01.
install postgres in your local PC.
create a database "ubuntu". 
Enclose the database name ubuntu with double quotes since postgres is case-sensitive.  
run the below sql commands to have necessary DB objects and references. 

# DB Scripts


create table "category"
(
    "cat_featured" boolean,
    "cat_url" character varying,
    "cat_url_key" character varying,
    "cat_parent_id" bigint,
    "cat_id" bigint not null,
    "cat_name" character varying not null,
    "cat_active" boolean,
    "cat_navigation_included" boolean,
    constraint "category_pkey" primary key ("cat_id")
);

create table "product"
(
    "prod_id" bigint not null,
    "prod_name" character varying not null,
    "prod_desc" text,
    "prod_url" character varying,
    "prod_active" boolean,
    "prod_new" boolean,
    "prod_retail_price" double precision,
    "prod_sale_price" double precision,
    "prod_discount" double precision,
    "prod_currency" jsonb,
    "prod_medias" jsonb,
    "cat_id" bigint not null,
    CONSTRAINT "product_pkey" primary key ("prod_id"),
    CONSTRAINT "fk_cat_id" foreign key ("cat_id")
        REFERENCES public.category ("cat_id") 
);

create table "sku"
(
    "sku_id" bigint not null,
    "sku_name" character varying not null,
    "sku_desc" text,
    "sku_short_desc" character varying,
    "sku_start_date" timestamp with time zone,
    "sku_retail_price" double precision,
    "sku_sale_price" double precision,
    "sku_base_price_unit" character varying,
    "sku_discount" double precision,
    "sku_active" boolean,
    "sku_available" boolean,
    "sku_inventory_type" character varying,
    "sku_ww" character varying,
    "sku_product_type" character varying,
    "sku_qty_available" bigint,
    "sku_subscription" boolean,
    "sku_tax_code" character varying,
    "prod_id" bigint not null,
    "sku_medias" jsonb,
    "sku_fulfillment_type" jsonb,
    constraint "sku_pkey" primary key ("sku_id"),
    constraint "fk_prod_id" foreign key ("prod_id")
        references public.product ("prod_id")
);



create sequence "product_id_next_value"
    increment by 1 
    minvalue 1
    no maxvalue
    start with 1 
    no cycle;

create sequence "sku_id_next_value"
    increment by 1 
    minvalue 1
    no maxvalue
    start with 1 
    no cycle;

create sequence "category_id_next_value"
    increment by 1 
    minvalue 1
    no maxvalue
    start with 1 
    no cycle;

  -- insert scripts for master category table

insert into category ( "cat_id", "cat_parent_id", "cat_name", "cat_active", "cat_featured", "cat_navigation_included", "cat_url", "cat_url_key" ) 
                values ( nextval('category_id_next_value'), null, 'Food', true, false, true, '/food/chips', 'food');
                
insert into category ( "cat_id", "cat_parent_id", "cat_name", "cat_active", "cat_featured", "cat_navigation_included", "cat_url", "cat_url_key" ) 
                values ( nextval('category_id_next_value'), 1, 'Tortilla Chips', true, false, true, '/food/Tortilla Chips', 'Tortilla Chips');
                
insert into category ( "cat_id", "cat_parent_id", "cat_name", "cat_active", "cat_featured", "cat_navigation_included", "cat_url", "cat_url_key" ) 
                values ( nextval('category_id_next_value'), 2, 'child Tortilla Chips', true, true, true, '/food/chips/child', 'child chips');

insert into category ( "cat_id", "cat_parent_id", "cat_name", "cat_active", "cat_featured", "cat_navigation_included", "cat_url", "cat_url_key" ) 
                values ( nextval('category_id_next_value'), 3, 'grand Tortilla Chips', true, true, true, '/food/chips/grand-Tortilla Chips', 'Tortilla Chips');

insert into category ( "cat_id", "cat_parent_id", "cat_name", "cat_active", "cat_featured", "cat_navigation_included", "cat_url", "cat_url_key" ) 
                values ( nextval('category_id_next_value'), 4, 'great grand Tortilla Chips', true, true, true, '/food/chips/great-grand', 'great grand');
                
insert into category ( "cat_id", "cat_parent_id", "cat_name", "cat_active", "cat_featured", "cat_navigation_included", "cat_url", "cat_url_key" ) 
                values ( nextval('category_id_next_value'), null, 'Kitchen', true, false, true, '/kitchen/drinkware', 'drinkware');
                
insert into category ( "cat_id", "cat_parent_id", "cat_name", "cat_active", "cat_featured", "cat_navigation_included", "cat_url", "cat_url_key" ) 
                values ( nextval('category_id_next_value'), 6, 'Kitchen', true, false, true, '/kitchen/drinkware', 'drinkware');

insert into category ( "cat_id", "cat_parent_id", "cat_name", "cat_active", "cat_featured", "cat_navigation_included", "cat_url", "cat_url_key" ) 
                values ( nextval('category_id_next_value'), 7, 'Kitchen', true, false, true, '/kitchen/drinkware', 'drinkware');			
                
                
insert into category ( "cat_id", "cat_parent_id", "cat_name", "cat_active", "cat_featured", "cat_navigation_included", "cat_url", "cat_url_key" ) 
                values ( nextval('category_id_next_value'), null, 'Lifestyle', true, false, true, '/lifestyle/small-appliances', 'small-appliances');

