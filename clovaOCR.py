import requests
import uuid
import time
import json

api_url = ''
secret_key = ''
image_file = ''

request_json = {
    'images': [
        {
            'format': 'jpg',
            'name': 'demo'
        }
    ],
    'requestId': str(uuid.uuid4()),
    'version': 'V2',
    'timestamp': int(round(time.time() * 1000))
}

payload = {'message': json.dumps(request_json).encode('UTF-8')}
files = [
  ('file', open(image_file,'rb'))
]
headers = {
  'X-OCR-SECRET': secret_key
}

response = requests.request("POST", api_url, headers=headers, data = payload, files = files)

result = response.json()

result_list = []
current_row = []
#result_list에 정보 배열로 저장
for image in result['images']:
    for field in image['fields']:
        if field['lineBreak']:
            current_row.append(field['inferText'])
            result_list.append(current_row)
            current_row = []
        else:
            current_row.append(field['inferText'])
result_list.append(current_row)

# 결과 출력
for row in result_list:
    print(row)
