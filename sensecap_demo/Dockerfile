# node镜像
FROM nginx:1.15-alpine

COPY ./sensecap_demo/dist /usr/share/nginx/html
#把vue项目打包后的 dist文件夹放在服务器的/usr/share/nginx/html 路径下
COPY ./sensecap_demo/nginx.conf /etc/nginx/conf.d/nginx.conf
#把项目里的nginx.conf 退换掉nginx里的nginx.conf


#RUN npm install
#COPY . .

#CMD ["npm","run","start"]

EXPOSE 80

