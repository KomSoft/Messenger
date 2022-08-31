DROP TYPE IF EXISTS file_types_enum CASCADE;
CREATE TYPE file_types_enum AS ENUM ('unknown', 'image', 'sound', 'msoffice_document', 'acrobat_document'); 

DROP TYPE IF EXISTS chat_types_enum CASCADE;
CREATE TYPE chat_types_enum AS ENUM ('personal_chat', 'group_chat', 'channel'); 

DROP TYPE IF EXISTS message_status CASCADE;
CREATE TYPE message_status AS ENUM ('read', 'unread', 'sent', 'delivered', 'deleted', 'edited'); 
