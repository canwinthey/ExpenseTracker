import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Home from "./views/Home.jsx";
import Expense from "./views/Expense.jsx";
function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/expenses" element={<Expense />} />
          <Route path="*" element={<Home />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
