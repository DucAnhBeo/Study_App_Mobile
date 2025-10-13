const express = require("express");
const mongoose = require("mongoose");
const cors = require("cors");

const app = express();
app.use(cors());
app.use(express.json());

mongoose.connect("mongodb+srv://lhd181204:rYaKka6yi50etbmm@test-mongoose.7ooswqb.mongodb.net/quiz_andoird_app?retryWrites=true&w=majority&appName=test-mongoose")
  .then(() => console.log("Connected"))
  .catch(err => console.error("Error:", err));


const QuestionSchema = new mongoose.Schema({
  question: String,
  correct_answer: String,
  incorrect_answers: [String]
});

const Question = mongoose.model("Question", QuestionSchema, "quiz_db");

app.get("/", (req, res) => {
  res.send("Server chạy ngon!");
});

app.get("/questions", async (req, res) => {
  try {
    console.log("Fetching questions with find...");
    const questions = await Question.find().limit(5);
    console.log("Got:", questions.length);
    res.json(questions);
  } catch (err) {
    console.error("Error in /questions:", err);
    res.status(500).json({ error: err.message });
  }
});



app.get("/test-insert", async (req, res) => {
  try {
    const newQ = new Question({
      question: "2 + 2 = ?",
      correct_answer: "4",
      incorrect_answers: ["3", "5", "22"]
    });
    await newQ.save();
    res.json({ msg: "Inserted!" });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

const PORT = 3000;
app.listen(PORT, () => console.log(`http://localhost:${PORT}`));


//app.get("/questions", async (req, res) => {
//  try {
//    console.log(">>> /questions called");
//    const questions = await Question.find().limit(5);
//    console.log(">>> Got", questions.length, "docs");
//    res.json(questions);
//  } catch (err) {
//    console.error(">>> Error in /questions:", err);
//    res.status(500).json({ error: err.message });
//  }
//});


//app.get("/questions", async (req, res) => {
//  try {
//    const questions = await Question.aggregate([{ $sample: { size: 10 } }]);
//    res.json(questions);
//  } catch (err) {
//    res.status(500).json({ error: err.message });
//  }
//});