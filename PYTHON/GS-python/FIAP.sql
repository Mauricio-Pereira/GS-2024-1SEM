drop table GS_PRODUCAO_PLASTICO;
drop table GS_DESPEJO_DE_PLASTICO;

CREATE TABLE GS_PRODUCAO_PLASTICO (
    Entidade VARCHAR2(100),
    Ano NUMBER(4),
    Producao_Anual_de_Plastico VARCHAR2(14)
);

CREATE TABLE GS_DESPEJO_DE_PLASTICO(
     Entidade VARCHAR2(100),
     Ano NUMBER(4),
     Participacao VARCHAR2(50)
);



select * from gs_producao_plastico;
SELECT * FROM GS_DESPEJO_DE_PLASTICO;



