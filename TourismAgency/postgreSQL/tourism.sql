PGDMP                       |            tourismagency    16.1    16.1 "    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16769    tourismagency    DATABASE     �   CREATE DATABASE tourismagency WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Turkish_Turkey.1254';
    DROP DATABASE tourismagency;
                postgres    false            �            1259    16770    hotel    TABLE     �  CREATE TABLE public.hotel (
    id bigint NOT NULL,
    name character varying NOT NULL,
    address character varying NOT NULL,
    mail character varying NOT NULL,
    phone character varying NOT NULL,
    star character varying NOT NULL,
    car_park boolean NOT NULL,
    wifi boolean NOT NULL,
    pool boolean NOT NULL,
    fitness boolean NOT NULL,
    concierge boolean NOT NULL,
    spa boolean NOT NULL,
    room_service boolean NOT NULL
);
    DROP TABLE public.hotel;
       public         heap    postgres    false            �            1259    16811    hotel_id_seq    SEQUENCE     �   ALTER TABLE public.hotel ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hotel_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    215            �            1259    16791    pension    TABLE     �   CREATE TABLE public.pension (
    id bigint NOT NULL,
    hotel_id bigint NOT NULL,
    pension_type character varying NOT NULL
);
    DROP TABLE public.pension;
       public         heap    postgres    false            �            1259    16812    pension_id_seq    SEQUENCE     �   ALTER TABLE public.pension ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.pension_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    218            �            1259    16784    reservation    TABLE     �  CREATE TABLE public.reservation (
    id bigint NOT NULL,
    room_id bigint NOT NULL,
    check_in_date date NOT NULL,
    check_out_date date NOT NULL,
    total_price double precision NOT NULL,
    guest_count bigint NOT NULL,
    guest_name character varying NOT NULL,
    guest_citizen_id character varying NOT NULL,
    guest_mail character varying NOT NULL,
    guest_phone character varying NOT NULL
);
    DROP TABLE public.reservation;
       public         heap    postgres    false            �            1259    16813    reservation_id_seq    SEQUENCE     �   ALTER TABLE public.reservation ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.reservation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    217            �            1259    16777    room    TABLE       CREATE TABLE public.room (
    id bigint NOT NULL,
    hotel_id bigint NOT NULL,
    pension_id bigint NOT NULL,
    season_id bigint NOT NULL,
    type character varying NOT NULL,
    stock bigint NOT NULL,
    adult_price double precision NOT NULL,
    child_price double precision NOT NULL,
    bed_capacity bigint NOT NULL,
    square_meter bigint NOT NULL,
    television boolean NOT NULL,
    minibar boolean NOT NULL,
    game_console boolean NOT NULL,
    cash_box boolean NOT NULL,
    projection boolean NOT NULL
);
    DROP TABLE public.room;
       public         heap    postgres    false            �            1259    16814    room_id_seq    SEQUENCE     �   ALTER TABLE public.room ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.room_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    216            �            1259    16798    season    TABLE     �   CREATE TABLE public.season (
    id bigint NOT NULL,
    hotel_id bigint NOT NULL,
    start_date date NOT NULL,
    finish_date date NOT NULL
);
    DROP TABLE public.season;
       public         heap    postgres    false            �            1259    16815    season_id_seq    SEQUENCE     �   ALTER TABLE public.season ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.season_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    219            �            1259    16803    user    TABLE     �   CREATE TABLE public."user" (
    id bigint NOT NULL,
    user_name character varying NOT NULL,
    user_password character varying NOT NULL,
    user_role character varying NOT NULL
);
    DROP TABLE public."user";
       public         heap    postgres    false            �            1259    16810    user_id_seq    SEQUENCE     �   ALTER TABLE public."user" ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    220            �          0    16770    hotel 
   TABLE DATA           �   COPY public.hotel (id, name, address, mail, phone, star, car_park, wifi, pool, fitness, concierge, spa, room_service) FROM stdin;
    public          postgres    false    215   �'       �          0    16791    pension 
   TABLE DATA           =   COPY public.pension (id, hotel_id, pension_type) FROM stdin;
    public          postgres    false    218   �(       �          0    16784    reservation 
   TABLE DATA           �   COPY public.reservation (id, room_id, check_in_date, check_out_date, total_price, guest_count, guest_name, guest_citizen_id, guest_mail, guest_phone) FROM stdin;
    public          postgres    false    217   �)       �          0    16777    room 
   TABLE DATA           �   COPY public.room (id, hotel_id, pension_id, season_id, type, stock, adult_price, child_price, bed_capacity, square_meter, television, minibar, game_console, cash_box, projection) FROM stdin;
    public          postgres    false    216   6*       �          0    16798    season 
   TABLE DATA           G   COPY public.season (id, hotel_id, start_date, finish_date) FROM stdin;
    public          postgres    false    219   V+       �          0    16803    user 
   TABLE DATA           I   COPY public."user" (id, user_name, user_password, user_role) FROM stdin;
    public          postgres    false    220   �+       �           0    0    hotel_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.hotel_id_seq', 6, true);
          public          postgres    false    222            �           0    0    pension_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.pension_id_seq', 35, true);
          public          postgres    false    223            �           0    0    reservation_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.reservation_id_seq', 31, true);
          public          postgres    false    224            �           0    0    room_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.room_id_seq', 43, true);
          public          postgres    false    225            �           0    0    season_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.season_id_seq', 24, true);
          public          postgres    false    226            �           0    0    user_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.user_id_seq', 18, true);
          public          postgres    false    221            4           2606    16776    hotel hotel_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.hotel
    ADD CONSTRAINT hotel_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.hotel DROP CONSTRAINT hotel_pkey;
       public            postgres    false    215            :           2606    16797    pension pension_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.pension
    ADD CONSTRAINT pension_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.pension DROP CONSTRAINT pension_pkey;
       public            postgres    false    218            8           2606    16790    reservation reservation_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.reservation DROP CONSTRAINT reservation_pkey;
       public            postgres    false    217            6           2606    16783    room room_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.room
    ADD CONSTRAINT room_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.room DROP CONSTRAINT room_pkey;
       public            postgres    false    216            <           2606    16802    season season_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.season
    ADD CONSTRAINT season_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.season DROP CONSTRAINT season_pkey;
       public            postgres    false    219            >           2606    16809    user user_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public."user" DROP CONSTRAINT user_pkey;
       public            postgres    false    220            �   �   x�mлj�0���)2g�n����\��R+*� م�e�
�ҩ[���oP����/ep�}�fS��h��QN�{��Pաq����7����>/&�碀�p�Ya8޾���NX�B4^[��J!�'�𨀞=��S뢴��>/�xG+D�g��[��Ays����j%T�di���g��COASg���Ƚo�=K8[����S*,���CJ���*n-�� ����ˠ�B�?���      �   �   x�u�1
�@E�S�	��&QKQ$`� ��,�dM`�B.��u�`��K
E%�?�!���KK�8���l-͜�L����VN(ю�W]�\�"���ʄ�r����y��F2�הJ%9�t��'S��ģ�u{��BHŵ��T����!��)�Q�|B�Wy��� �-�WB      �   o   x�M�1�@��+� �y�vB���PpH��?F����[	#Xh��Z5�Z20�o�c�yT�>s����5�wx���NXa�5��Z���'���2u���yX�!�ADޓ�!7      �     x�m�MN�0F�_N����k� �Y��T���2g�ͽ��dZI�6/��Ґ���xx9w��U_���2�sB�lcvaDD�o�?����"�_ ���B$p{z���������d;H�TF����A�孊A^�ћ���<.�)w.����ڦ���M�}c�-!��&�D���%	��<Z8k�O�eY�Գ�;ʑ���h8�+_TF�&����;�hz����Aᯟ���o���yˏ���K/��:�{�q�� D
��      �   Y   x�u�A
� D���.3���K���P�@>O
Vz�:�}NQ���;��fj`�z�M��L]�6u��iUT�L1Ҧ=SiU�!"��7T      �   J   x�34�LO!G_O?.cδ4���9A��7��?�Օ�Д���"f�y��y��1μ<����� /0A     