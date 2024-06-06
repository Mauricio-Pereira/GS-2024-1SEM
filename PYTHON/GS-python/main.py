# Luiz Otávio - RM553542
# Mauricio Pereira - RM553748
# Vitor Onofre - RM553241


import oracledb
import csv
from colorama import Fore, Style


def connection():
    try:
        conn = oracledb.connect(user="rm553542", password="290405", host="oracle.fiap.com.br", port="1521",
                                service_name="ORCL")
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
            ano = int(row['Ano'])  # converte ano para string
            producao_anual_de_plastico = row['Producao_Anual_de_Plastico']

            # Verifica se os dados já existem no banco de dados
            cursor.execute("SELECT 1 FROM GS_PRODUCAO_PLASTICO WHERE Entidade = :1 AND Ano = :2 AND Producao_Anual_de_Plastico = :3", [entidade, ano, producao_anual_de_plastico])
            result = cursor.fetchone()

            # Se os dados não existirem insere-os no banco de dados
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
    print("\n{:<20} {:<10} {:<30}".format("Entidade", "Ano", "Produção Anual de Plástico"))
    print("-" * 60)
    for row in cursor:
        print("{:<20} {:<10} {:<30}".format(row[0], row[1], row[2] + "t"))
    cursor.close()
    conn.close()

def select_producao_global_por_ano(ano):
    conn = connection()
    cursor = conn.cursor()
    cursor.execute("SELECT SUM(Producao_Anual_de_Plastico) FROM GS_PRODUCAO_PLASTICO WHERE Ano = :1 GROUP BY Ano", [ano])
    result = cursor.fetchone()
    if result is not None:
        print(f"Produção Anual de Plástico para o ano {ano}: {result[0]}" + " Toneladas")
    else:
        print(f"Não foram encontrados dados para o ano {ano}.")
    cursor.close()
    conn.close()

def select_despejo_plastico():
    conn = connection()
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM GS_DESPEJO_DE_PLASTICO")
    print("\n{:<30} {:<15} {:<40}".format("Entidade", "Ano", "Participação"))
    print("-" * 85)
    for row in cursor:
        print("{:<30} {:<15} {:<40}".format(row[0], row[1], row[2] + "%"))
    cursor.close()
    conn.close()


def select_despejo_plastico_por_pais(pais):
    if not pais or not isinstance(pais, str):
        print("O parâmetro 'pais' deve ser uma string não vazia.")
        return

    conn = connection()
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM GS_DESPEJO_DE_PLASTICO WHERE LOWER(Entidade) = :1", [pais.lower()])
    rows = cursor.fetchall()

    if not rows:
        print(f"Não foram encontrados dados para o país {pais}.")
    else:
        for row in rows:
            print(f"Entidade: {row[0]}, Ano: {row[1]}, Participação: {row[2]}" + "%")

    cursor.close()
    conn.close()

def menu_principal():
    print(Fore.CYAN + "\n" + "-" * 30)
    print(Style.BRIGHT + "Menu Principal")
    print(Fore.CYAN + "-" * 30)
    print(Fore.WHITE + "1 - Produção Global de Plástico")
    print("2 - Despejo de Plástico por País")
    print("0 - Sair")
    print(Fore.CYAN + "-" * 30 + "\n" + Style.RESET_ALL)


def menu_secundario_producao_global():
    print(Fore.GREEN + "\n" + "-" * 30)
    print(Style.BRIGHT + "Menu Secundário - Produção Global de Plástico")
    print(Fore.GREEN + "-" * 30)
    print(Fore.WHITE + "1 - Visualizar todos os dados")
    print("2 - Filtrar por ano")
    print(Fore.GREEN + "\n" + "-" * 30 + "\n" + Style.RESET_ALL)



def menu_secundario_despejo_plastico():
    print(Fore.YELLOW + "\n" + "-" * 30)
    print(Style.BRIGHT + "Menu Secundário - Despejo de Plástico")
    print(Fore.YELLOW + "-" * 30)
    print(Fore.WHITE + "1 - Visualizar todos os dados")
    print("2 - Filtrar por país")
    print(Fore.YELLOW + "\n" + "-" * 30 + "\n" + Style.RESET_ALL)


def main():
    importar_csv_producao_plastico('1- producao-de-plastico-global.csv')
    importar_csv_despejo_plastico('2- participacao-despejo-residuo-plastico.csv')
    while True:
        menu_principal()
        opcao = input("Escolha uma opção: ")

        match opcao:
            case "1":
                menu_secundario_producao_global()
                opcao_producao = input("Escolha uma opção: ")

                match opcao_producao:
                    case "1":
                        select_producao_global()
                    case "2":
                        ano = int(input("Digite o ano: "))
                        select_producao_global_por_ano(ano)
                    case _:
                        print("Opção inválida!")
            case "2":
                menu_secundario_despejo_plastico()
                opcao_despejo = input("Escolha uma opção: ")

                match opcao_despejo:
                    case "1":
                        select_despejo_plastico()
                    case "2":
                        pais = input("Digite o nome do país(em inglês): ")
                        select_despejo_plastico_por_pais(pais)
                    case _:
                        print("Opção inválida!")
            case "0":
                print("Finalizando...")
                break
            case _:
                print("Opção inválida!")


if __name__ == '__main__':
    main()
