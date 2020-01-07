# node images

FROM mkenney/npm:node-8-alpine
COPY ./sensecap_demo /sensecap_demo
RUN cd /sensecap_demo && npm install && npm run-script build


FROM nginx:1.15-alpine
COPY --from=0 /sensecap_demo/dist /usr/share/nginx/html
COPY ./sensecap_demo/nginx.conf /etc/nginx/conf.d/nginx.conf
EXPOSE 80

