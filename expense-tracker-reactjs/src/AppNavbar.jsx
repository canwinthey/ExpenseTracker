import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import { Link } from "react-router-dom";
import { ExpenseIcon } from "./appicons";

function AppNavbar() {
  return (
    <Navbar bg="primary" data-bs-theme="dark" fixed="top" expand="lg">
      {/* <Container> */}
      <Navbar.Brand href="#home">
        <ExpenseIcon />
        Expense Tracker App
      </Navbar.Brand>
      <Nav className="ml-auto">
        <Link to="/">Home</Link>
        <Link to="/expenses">Expense</Link>
      </Nav>
      {/* </Container> */}
    </Navbar>
  );
}

export default AppNavbar;
