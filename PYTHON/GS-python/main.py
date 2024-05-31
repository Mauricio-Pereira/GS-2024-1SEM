import oracledb
import csv


def connection():
    try:
        conn = oracledb.connect(user="rm553542", password="290405", host="oracle.fiap.com.br", port="1521",
                                service_name="ORCL")
        print("Conexão bem-sucedida!")
        return conn
    except oracledb.DatabaseError as e:
        print("Houve um problema ao conectar ao banco de dados.")
        print("Erro: ", e)
        return None


def importar_csv_producao_plastico(file):
    conn = connection()
    cursor = conn.cursor()
    with open(file, 'r') as file:
        reader = csv.DictReader(file)
        for row in reader:
            entidade = row['Entidade']
            ano = int(row['Ano'])  # Converte 'Ano' para um número
            producao_anual_de_plastico = row['Producao_Anual_de_Plastico']

            # Verifica se os dados já existem no banco de dados
            cursor.execute("SELECT 1 FROM GS_PRODUCAO_PLASTICO WHERE Entidade = :1 AND Ano = :2 AND Producao_Anual_de_Plastico = :3", [entidade, ano, producao_anual_de_plastico])
            result = cursor.fetchone()

            # Se os dados não existirem, insere-os no banco de dados
            if result is None:
                cursor.execute("INSERT INTO GS_PRODUCAO_PLASTICO (Entidade, Ano, Producao_Anual_de_Plastico) VALUES (:1, :2, :3)", [entidade, ano, producao_anual_de_plastico])

    conn.commit()
    cursor.close()
    conn.close()


def importar_csv_despejo_plastico(file):
    conn = connection()
    cursor = conn.cursor()
    with open(file, 'r') as file:
        reader = csv.DictReader(file, delimiter=',')  # Use a vírgula como delimitador
        for row in reader:
            entidade = row['Entidade']
            ano = int(row['Ano'])
            participacao = row['Participacao']

            # Verifica se os dados já existem no banco de dados
            cursor.execute("SELECT 1 FROM GS_DESPEJO_DE_PLASTICO WHERE Entidade = :1 AND Ano = :2 AND Participacao = :3",[entidade, ano, participacao])
            result = cursor.fetchone()

            # Se os dados não existirem, insere-os no banco de dados
            if result is None:
                cursor.execute("INSERT INTO GS_DESPEJO_DE_PLASTICO (Entidade, Ano, Participacao) VALUES (:1, :2, :3)", [entidade, ano, participacao])

    conn.commit()
    cursor.close()
    conn.close()


def select_producao_global():
    conn = connection()
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM GS_PRODUCAO_PLASTICO")
    for row in cursor:
        print(f"Entidade: {row[0]}, Ano: {row[1]}, Produção Anual de Plástico: {row[2]}")
    cursor.close()
    conn.close()

def select_despejo_plastico():
    conn = connection()
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM GS_DESPEJO_DE_PLASTICO")
    for row in cursor:
        print(f"Entidade: {row[0]}, Ano: {row[1]}, Participação: {row[2]}")
    cursor.close()
    conn.close()


def main():
    conn = connection()
    importar_csv_producao_plastico('1- producao-de-plastico-global.csv')
    importar_csv_despejo_plastico('2- participacao-despejo-residuo-plastico.csv')
    while True:
        print("\n***** Digite a opção desejada: *****")
        print("1 - Produção Global de Plástico")
        print("2 - Despejo de Plástico por País")
        print("0 - Sair\n")
        opcao = int(input("Escolha uma opção: "))

        match opcao:
            case 1:
                select_producao_global()
            case 2:
                select_despejo_plastico()
            case 3:
                break
            case _:
                print("Opção inválida!")


if __name__ == '__main__':
    main()
