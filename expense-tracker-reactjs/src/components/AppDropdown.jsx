import Form from "react-bootstrap/Form";

function AppDropDown({options,onChangecall,name,label}) {
    return ( 
        <Form.Group className="d-flex gap-1" controlId="formGroupProjectID">
              <Form.Label className="mt-2">{label}</Form.Label>
              <Form.Select
                onChange={(e) => onChangecall(e)}
                name={name}
                aria-label={"select" + label} 
              >
                <option>select {label}</option>
                {<option value="100 - CITI">100 - CITI</option>}
                <option value="100 - CITI">100 - CITI</option>
                <option value="200 - MorganStanley">200 - MorganStanley</option>
                <option value="300 - HSBC">300 - HSBC</option>
                <option value="400 - WellsFargo">400 - WellsFargo</option>
              </Form.Select>
            </Form.Group>
      );
}

export default AppDropDown;