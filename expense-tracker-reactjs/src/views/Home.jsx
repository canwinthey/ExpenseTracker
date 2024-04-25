import AppNavbar from "../AppNavbar.jsx";

function Home() {
  return (
    <div>
      <AppNavbar />
      <h2
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          height: "100vh",
        }}
      >
        Welcome to easy expense app !
      </h2>
    </div>
  );
}

export default Home;
