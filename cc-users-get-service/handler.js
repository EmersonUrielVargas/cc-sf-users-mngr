const serverless = require("serverless-http");
const express = require("express");
const { getUser } = require("./src/service/userService");
require('dotenv').config();

const app = express();

app.get("/v1/users/:id", async (req, res, next) => {
  const userId = req.params.id;
  const response = await getUser(userId);
  return res.status(200).json(response);
});

app.use((req, res, next) => {
  return res.status(404).json({
    error: "Not Found",
  });
});

exports.handler = serverless(app);
