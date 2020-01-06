# node images
FROM nginx:1.15-alpine

COPY ./sensecap_demo/dist /usr/share/nginx/html
COPY ./sensecap_demo/nginx.conf /etc/nginx/conf.d/nginx.conf
EXPOSE 80

