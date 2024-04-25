import Button from "react-bootstrap/Button";
import Table from "react-bootstrap/Table";
import AppNavbar from "../AppNavbar.jsx";
import Form from "react-bootstrap/Form";
import { useEffect, useState } from "react";
import axios from "axios";
import AppScreenLoader from "../components/AppScreenLoader.jsx";
import AppToaster from "../components/AppToaster.jsx";
import moment from "moment";
function Expense(props) {
  const categoryEndpoint = "http://20.221.74.206:8100/category";
  const locationEndpoint = "http://20.221.74.206:8200/location/state/";
  const stateEndpoint = "http://20.221.74.206:8200/state";
  const expensesEndpoint = "http://20.221.74.206:8080/expenses/";

  const [expenseItem, setExpenseItem] = useState({ 
    categoryId: 1,
     stateId: 1,
     locationId: 1,
    currency: "GBP",
    costCode: "100",
    expenseDate:moment(new Date().toLocaleDateString()).format("YYYY-MM-DD")})
  const [categories, setCategories] = useState(null);
  const [locations, setLocations] = useState(null);
  const [expenses, setExpenses] = useState(null);
  const [showLoader, setshowLoader] = useState(false);
  const [states, setStates] = useState(null);
  const [showToaster, setShowToaster] = useState(false);
  const [toasterMessage, setToasterMessage] = useState("");
  const [toasterVariant, setToasterVariant] = useState("Success");

  const costCodeObj = {
    '100': "100 - Airfare",
    '200': "200 - Team Meals",
    '300': "300 - Event Tickets",
    '400': "400 - Catering Services",
  };
  const handleFormChange = (event) => {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = { ...expenseItem };
    if (
      name !== "description" &&
      name !== "expenseDate" &&
      name !== "currency"
    ) {
      item[name] = Number(value);
    } else {
      item[name] = value;
    }
    setExpenseItem(item);
    if (name === "stateId") {
      fetchLocationByStateId(value);
    }
    // console.log(item);
  };

  const fetchCategories = async () => {
    setshowLoader(true);
    try {
      const response = await fetch(categoryEndpoint);
      const categories = await response.json();
      setCategories(categories);
      setshowLoader(false);
    } catch (error) {
      console.error("Error fetching data:", error);
      setshowLoader(false);
    }
  };

  const fetchState = async () => {
    setshowLoader(true);
    try {
      const response = await fetch(stateEndpoint);
      console.log(response);
      const state = await response.json();
      setStates(state);
      setshowLoader(false);
    } catch (error) {
      console.error("Error fetching data:", error);
      setshowLoader(false);
    }
  };
  const fetchLocationByStateId = async (id) => {
    console.log(locationEndpoint + id.toString());
    setshowLoader(true);
    try {
      const response = await fetch(locationEndpoint + id.toString());

      const locations = await response.json();
      console.log(locations);
      setLocations(locations);
      setshowLoader(false);
    } catch (error) {
      console.error("Error fetching data:", error);
      setshowLoader(false);
    }
  };

  const fetchAllExpenses = async () => {
    setshowLoader(true);
    try {
      const response = await fetch(expensesEndpoint);
      const expenses = await response.json();
      setExpenses(expenses);
      setshowLoader(false);
    } catch (error) {
      console.error("Error fetching data:", error);
      setshowLoader(false);
    }
  };

  const handleSaveExpense = async () => {
    setshowLoader(true);
    try {
      const response = await fetch(expensesEndpoint, {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify(expenseItem),
      });
      const expenseData = await response.json();
      console.log("expense created:", response);
      setshowLoader(false);
      if (response?.status === 200 || response?.status === 201) {
        fetchAllExpenses();
        setToasterMessage("Expense succesfully added!!");
        setToasterVariant("Success");
        setShowToaster(true);
      } else {
        setToasterMessage(expenseData?.message);
        setToasterVariant("Danger");
        setShowToaster(true);
      }
    } catch (error) {
      console.error("Error creating Expense:", error);
      setshowLoader(false);
      setToasterMessage(error?.message);
      setToasterVariant("Danger");
      setShowToaster(true);
    }
  };

  const handleDeleteExpense = async (id) => {
    setshowLoader(true);
    try {
      const response = await axios.delete(expensesEndpoint + id?.toString());
      console.log("expense deleted:", response);
      setshowLoader(false);
      setToasterMessage("Expense deleted successfully!");
      setToasterVariant("Success");
      setShowToaster(true);
    } catch (error) {
      console.error("Error creating Expense:", error);
      setshowLoader(false);
      setToasterMessage(error?.message);
      setToasterVariant("Danger");
      setShowToaster(true);
    }
    fetchAllExpenses();
  };

  useEffect(() => {
    fetchCategories();
    fetchState();
    fetchLocationByStateId(expenseItem?.locationId)
    fetchAllExpenses();

  }, []);

  return (
    <>
      <AppNavbar />
      <section className="expense">
        <h3 className="d-flex">Add Expense</h3>
        <div className="expense_form d-flex mt-4">
          <Form className="d-flex gap-5 w-100 flex-wrap">
            <Form.Group className="d-flex gap-1 flex-column" controlId="formGroupcostCode">
              <Form.Label className="mt-2">Cost code</Form.Label>
              <Form.Select
                onChange={(e) => handleFormChange(e)}
                name="costCode"
                aria-label=" select cost Code"
                value={expenseItem?.costCode}
              >
                <option>select cost code</option>
                <option value="100">100 - Airfare</option>
                <option value="200">200 - Team Meals</option>
                <option value="300">300 - Event Tickets</option>
                <option value="400">400 - Catering Services</option>
              </Form.Select>
            </Form.Group>
            <Form.Group
              className="d-flex gap-1 flex-column"
              controlId="formGroupDescription"
            >
              <Form.Label className="mt-2">Description</Form.Label>
              <Form.Control
                name="description"
                onChange={(e) => handleFormChange(e)}
                type="text"
                placeholder="Enter description"
              />
            </Form.Group>

            <Form.Group className="d-flex gap-1 flex-column" controlId="formGroupCategory">
              <Form.Label className="mt-2">Category</Form.Label>
              <Form.Select
                onChange={(e) => handleFormChange(e)}
                name="categoryId"
                aria-label="Default select example"
                value={expenseItem?.categoryId}
                defaultValue={1}
              >
                <option>Open this select menu</option>
                {categories?.map((cat, index) => (
                  <option key={` ${cat?.id} - ${cat?.name}`} value={cat?.id}>
                    {" "}
                    {cat?.name}
                  </option>
                ))}
              </Form.Select>
            </Form.Group>

            <Form.Group className="d-flex gap-1 flex-column" controlId="formGroupState">
              <Form.Label className="mt-2">Destination State</Form.Label>
              <Form.Select
                onChange={(e) => handleFormChange(e)}
                name="stateId"
                aria-label=" select state"
                value={expenseItem?.stateId}

              >
                <option>select state</option>
                {states?.map((stat, index) => (
                  <option
                    key={` ${stat?.id} - ${stat?.state}`}
                    value={stat?.id}
                  >
                    {" "}
                    {stat?.state}
                  </option>
                ))}
              </Form.Select>
            </Form.Group>
            <Form.Group className="d-flex gap-1  flex-column" controlId="formGroupLocation">
              <Form.Label className="mt-2">Destination Location</Form.Label>
              <Form.Select
                onChange={(e) => handleFormChange(e)}
                name="locationId"
                aria-label=" select location"
                value={expenseItem?.locationId}
              >
                <option>select location</option>
                {locations?.map((loc, index) => (
                  <option
                    key={` ${loc?.id} - ${loc?.location}`}
                    value={loc?.id}
                  >
                    {" "}
                    {loc?.location}
                  </option>
                ))}
              </Form.Select>
            </Form.Group>
            <Form.Group className="d-flex gap-1 flex-column" controlId="formGroupAmount">
              <Form.Label className="mt-2">Amount</Form.Label>
              <Form.Control
                name="amount"
                step="0.05"
                type="number"
                placeholder="Enter amount"
                onChange={(e) => handleFormChange(e)}
              />
            </Form.Group>
            <Form.Group className="d-flex gap-1 flex-column" controlId="formGroupCurrency">
              <Form.Label className="mt-2">Currency</Form.Label>
              <Form.Select
                onChange={(e) => handleFormChange(e)}
                name="currency"
                aria-label=" select currency"
                value={expenseItem?.currency}

              >
                <option>select currency</option>
                <option value="INR">INR</option>
                <option value="EUR">EUR</option>
                <option value="AUD">AUD</option>
                <option value="GBP">GBP</option>
              </Form.Select>
            </Form.Group>

            <Form.Group className="d-flex gap-1 flex-column" controlId="formGroupDate">
              <Form.Label className="mt-2">Date</Form.Label>
              <Form.Control
                onChange={(e) => handleFormChange(e)}
                name="expenseDate"
                type="date"
                placeholder="Enter date"
                value={expenseItem?.expenseDate}
              />
            </Form.Group>
          </Form>
        </div>
        <div className="d-flex gap-2 my-4 justify-content-end">
          <Button onClick={() => handleSaveExpense()} variant="primary">
            Save
          </Button>
          <Button variant="outline-secondary">Cancel</Button>
        </div>
        <hr />
        <h4 className="d-flex my-4">Expense List</h4>

        <Table responsive striped bordered hover>
          <thead>
            <tr>
              <th>Sr#</th>
              <th>Cost Code</th>
              <th>Description</th>
              <th>Date</th>
              <th>State</th>
              <th>Location</th>
              <th>Category</th>
              <th>Currency</th>
              <th>Amount</th>
              <th>Amount(in USD)</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {expenses?.map((exp, index) => (
              <tr key={index}>
                <td>{`${index + 1}`}</td>
                <td>{costCodeObj?.[exp?.costCode?.toString()]}</td>
                <td>{exp?.description}</td>
                <td>{moment(exp?.expenseDate).format("DD-MM-YYYY")}</td>
                <td>{exp?.state}</td>
                <td>{exp?.location}</td>
                <td>{exp?.category}</td>
                <td>{exp?.currency}</td>
                <td>{exp?.amount}</td>
                <td>{exp?.currencyAmtInUSD}</td>
                <td>
                  <Button
                    onClick={() => handleDeleteExpense(exp?.id)}
                    variant="primary"
                  >
                    Delete
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      </section>

      {showLoader && <AppScreenLoader />}
      <AppToaster
        showToaster={showToaster}
        setShowToaster={setShowToaster}
        variant={toasterVariant}
        message={toasterMessage}
      />
    </>
  );
}

export default Expense;
