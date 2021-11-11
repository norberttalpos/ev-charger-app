FROM node:lts-alpine

WORKDIR /app

ADD . /app

RUN npm install
RUN npm run build

EXPOSE 8081
CMD [ "npm", "run", "serve" ]