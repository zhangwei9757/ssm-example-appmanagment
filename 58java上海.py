import requests
import re
import json
from bs4 import BeautifulSoup

def getDynamicURL():
    commentURL='http://sh.58.com/job/pn{}/?key=java&final=1&jump=1'
    count=0
    for i in range(10):
        print('\n第',i+1,'页')
        pageUrl=commentURL.format(i+1)
        res=requests.get(pageUrl)
        soup=BeautifulSoup(res.text,'html.parser')
        
        for job_item in soup.select('.job_item'):
            for job_name in job_item.select('.job_name'):
               jobInfo=job_name.select('a')[0].text.split('|')
               jobPostion=jobInfo[0].strip()
               jobName=jobInfo[1].strip().rstrip('…')
               jobMoney=job_item.select('p')[0].text
               jobUrl=re.search(('http://(.*).psid'),job_name.select('a')[0]['href']).group(1).rstrip('?')
               
               if 'java' in jobName:
                   if '8000' in jobMoney:
                       print('%2s%30s%30s%80s'%(jobPostion,jobName,jobMoney,jobUrl))
                       count+=1
               
    return count
           
print('\n筛选之后一共查询到：',getDynamicURL())
