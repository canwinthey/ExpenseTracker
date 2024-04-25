import Spinner from "react-bootstrap/Spinner";
function AppScreenLoader() {
  return (
    <div
      style={{
        display: "block",
        width: 700,
        padding: 30,
        position: "fixed",
        height: "100%",
        width: "100%",
        top: 0,
        left: 0,
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        background: "#1a1a1a",
        opacity: 0.8,
      }}
    >
      <Spinner animation="border" variant="primary" /> <br />
    </div>
  );
}

export default AppScreenLoader;
