const serverless = require("serverless-http");
const express = require("express");
const { createUser } = require("./src/service/userService");
require('dotenv').config();

const app = express();

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.post("/v1/users", async (req, res, next) => {
  const userData = req.body;
  console.log("Received user data:", userData);
  const response = await createUser(userData);
  if (response.statusCode !== 200) {
    return res.status(response.statusCode).json({
      message: "Error creating user",
      user: response.data
    });
  }
  return res.status(201).json({
    message: "user created sucessfull",
    user: response.data
  });
});


app.use((req, res, next) => {
  return res.status(404).json({
    error: "Not Found",
  });
});

exports.handler = serverless(app);
