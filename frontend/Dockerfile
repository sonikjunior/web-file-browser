# build stage
FROM node:16-alpine as build
WORKDIR /workspace
COPY package.json package-lock.json ./
RUN npm install
COPY . .

# run stage
ENTRYPOINT ["npm", "start"]
