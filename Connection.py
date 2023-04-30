import mariadb
import sys
from datetime import datetime

# mariadb 연결 설정
config = {
    'user': 'webuser',
    'password': 'webuser',
    'host': 'localhost',
    'port': 3306, # mariadb 포트 번호
    'database': 'webdb'
}

# mariadb 연결
try:
    conn = mariadb.connect(**config)
except mariadb.Error as e:
    print(f"Error connecting to MariaDB Platform: {e}")
    sys.exit(1)

result_list = [
['1', 'BL11', '중국', '고추', '2022-08-06', '2023-09-01', '20', '10', '1,354.24', '22,340,000'],
['2', 'BC12', '중국', '배추', '2022-08-07', '2023-09-01', '20', '20', '344.23', '230,406'],
['3', 'BE13', '중국', '귤', '2022-08-08', '2023-09-01', '20', '30', '6,354.24', '223,408,070'],
['4', 'BX14', '중국', '감', '2022-08-09', '2023-09-01', '20', '40', '1,354.24', '22,340,020'],
['5', 'BZ15', '중국', '사과', '2022-08-10', '2023-09-01', '20', '50', '1,354.24', '3,458,900'],
[]
]

# 커서 생성
cur = conn.cursor()

sql = "INSERT INTO OCRTABLE1 (num, code, origin, cultivar, indate, outdate, weight, count, price, won, extra) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"

# SQL 쿼리 실행
for data in result_list:
    if not data or not data[0]:
        continue

    values = (
        int(data[0]),
        data[1],
        data[2],
        data[3],
        datetime.strptime(data[4], "%Y-%m-%d").date(),
        datetime.strptime(data[5], "%Y-%m-%d").date(),
        int(data[6]),
        int(data[7]),
        data[8],
        data[9],
        ""
    )
    try:
        cur.execute(sql, values)
        conn.commit()
        print(f"Data inserted successfully into OCRTABLE1.")
    except mariadb.Error as e:
        print(f"Error inserting data into OCRTABLE1: {e}")

cur.execute("select * from ocrtable1")
# 결과 출력
for row in cur:
    print(row)

cur.execute("commit")
# 연결 종료
conn.close()
