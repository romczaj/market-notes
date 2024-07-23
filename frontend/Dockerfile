FROM node:22-alpine3.19 as build
WORKDIR /app
COPY ./ /app
RUN npm install
RUN npm run build

FROM nginx:1.21.0-alpine
COPY --from=build /app/dist/market-notes-frontend/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
