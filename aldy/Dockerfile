FROM node:alpine

WORKDIR /usr/app

RUN npm i --global pm2

COPY ./package*.json ./
COPY --chown=node:node package.json .

RUN npm i

COPY ./ ./


RUN npm run build

EXPOSE 3000
COPY --chown=node:node . .

USER node

# CMD [ "npm", "start" ]
CMD [ "pm2-runtime", "npm", "--", "start" ]

