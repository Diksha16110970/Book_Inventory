
// import React, { useState, useEffect } from "react";
// import Card from "./Card"; // Import the Card component
// import { useNavigate } from 'react-router-dom';

// const Dashboard = () => {
//   const navigate = useNavigate();
//   const [data, setData] = useState({
//     books: 0,
//     authors: 0,
//     categories: 0,
//   });

//   useEffect(() => {
//     // Fetch category count from the backend
//     const fetchCategoryCount = async () => {
//       try {
//         const response = await fetch("http://localhost:8080/api/auth/category-count", {
//           method: "GET",
//           headers: {
//             "Content-Type": "application/json",
//           },
//         });

//         if (!response.ok) {
//           throw new Error(`Error fetching category count: ${response.status}`);
//         }

//         const count = await response.json();  // This will be the category count
//         setData((prevState) => ({
//           ...prevState,
//           categories: count,  // Set category count to the state
//         }));
//       } catch (error) {
//         console.error("Error fetching category count:", error.message);
//       }
//     };

//     // Decode the JWT token manually
//   const getUserId = () => {
//     const token = localStorage.getItem('token');
//     if (token) {
//       try {
//         const payload = token.split('.')[1];  // Get the payload (second part)
//         const decodedPayload = JSON.parse(atob(payload));  // Decode and parse the payload
//         console.log('Decoded Payload:', decodedPayload); // Log to check the content
//         console.log(decodedPayload.sub);
//         return decodedPayload.sub;  // Use 'sub' field for user_id
//       } catch (error) {
//         console.error('Error decoding token:', error);
//         return null;
//       }
//     }
//     return null;
//   };

//   const getAuthHeader = () => {
//     const token = localStorage.getItem('token');
//     return token ? { Authorization: `Bearer ${token}` } : {};
//   };
//     const fetchAuthorCount = async () => {
//       try {
//         const response = await fetch("http://localhost:8080/api/auth/author-count", {
//           method: "GET",
//           headers: {
//             "Content-Type": "application/json",
//           },
//         });

//         if (!response.ok) {
//           throw new Error(`Error fetching author count: ${response.status}`);
//         }

//         const count = await response.json();  // This will be the author count
//         setData((prevState) => ({
//           ...prevState,
//           authors: count,  // Set author count to the state
//         }));
//       } catch (error) {
//         console.error("Error fetching author count:", error.message);
//       }
//     };
//     const fetchBookCount = async () => {
//       try {
//         const response = await fetch("http://localhost:8080/api/auth/book-count", {
//           method: "GET",
//           headers: {
//             "Content-Type": "application/json",
//           },
//         });

//         if (!response.ok) {
//           throw new Error(`Error fetching book count: ${response.status}`);
//         }

//         const count = await response.json();  // This will be the book count
//         setData((prevState) => ({
//           ...prevState,
//           books: count,  // Set book count to the state
//         }));
//       } catch (error) {
//         console.error("Error fetching book count:", error.message);
//       }
//     };
//     fetchCategoryCount();
//     fetchAuthorCount(); // Call the function to fetch the category count
//     fetchBookCount();
//   }, []);  // This effect runs once when the component mounts
//   const handleLogout = () => {
//         localStorage.removeItem('token');
//         navigate('/');
//       };

//   return (
//     <div className="dashboard-container">
//       <div className="sidebar">
//       <ul>
//           <li onClick={() => navigate('/dashboard')}>Dashboard</li>
//            <li onClick={() => navigate('/authors')}>Authors</li>
//            <li onClick={() => navigate('/book')}>Book</li>
//            <li onClick={() => navigate('/category')}>Category</li>
//            <li onClick={handleLogout}>Logout</li>  {/* Logout button */}
//          </ul>
//       </div>
//       <div className="main-content">
//         <h2>Dashboard Overview</h2>
//         <div style={{ display: "flex", justifyContent: "space-between" }}>
//           <Card title="Total Books" count={data.books} color="#4caf50" />
//           <Card title="Total Authors" count={data.authors} color="#007bff" />
//           <Card title="Total Categories" count={data.categories} color="#dc3545" />
//         </div>
//       </div>
//       <footer style={{
//         padding: '10px',
//         backgroundColor: 'rgb(149 153 157)',
//         textAlign: 'center',
//         fontSize: '14px',
//         color: 'white',
//         borderTop: '1px solid #dee2e6',
//         marginTop: 'auto',
//         width: '100%',
//         position: 'absolute',
//         bottom: '0',
//         left: '0',
//       }}>
//         <p>&copy; {new Date().getFullYear()} Diksha Nagdevate</p>
//       </footer>
      
//     </div>
//   );
// };



// export default Dashboard;


// import React, { useState, useEffect } from "react";
// import Card from "./Card"; // Import the Card component
// import { useNavigate } from 'react-router-dom';

// const Dashboard = () => {
//   const navigate = useNavigate();
//   const [data, setData] = useState({
//     books: 0,
//     authors: 0,
//     categories: 0,
//   });
//   const [username, setUsername] = useState("");  // State to store the username

//   useEffect(() => {
//     // Decode the JWT token manually and extract name
//     const getUserDetails = () => {
//       const token = localStorage.getItem('token');
//       if (token) {
//         try {
//           const payload = token.split('.')[1];  // Get the payload (second part)
//           const decodedPayload = JSON.parse(atob(payload));  // Decode and parse the payload
//           console.log('Decoded Payload:', decodedPayload); // Log to check the content
//           setUsername(decodedPayload.name);  // Extract and set the name from the JWT payload
//           return decodedPayload.sub;  // Use 'sub' field for user_id
//         } catch (error) {
//           console.error('Error decoding token:', error);
//           return null;
//         }
//       }
//       return null;
//     };

//     // Fetch category, author, and book counts from the backend
//     const fetchCategoryCount = async () => {
//       try {
//         const response = await fetch("http://localhost:8080/api/auth/category-count", {
//           method: "GET",
//           headers: {
//             "Content-Type": "application/json",
//           },
//         });

//         if (!response.ok) {
//           throw new Error(`Error fetching category count: ${response.status}`);
//         }

//         const count = await response.json();
//         setData((prevState) => ({
//           ...prevState,
//           categories: count,
//         }));
//       } catch (error) {
//         console.error("Error fetching category count:", error.message);
//       }
//     };

//     const fetchAuthorCount = async () => {
//       try {
//         const response = await fetch("http://localhost:8080/api/auth/author-count", {
//           method: "GET",
//           headers: {
//             "Content-Type": "application/json",
//           },
//         });

//         if (!response.ok) {
//           throw new Error(`Error fetching author count: ${response.status}`);
//         }

//         const count = await response.json();
//         setData((prevState) => ({
//           ...prevState,
//           authors: count,
//         }));
//       } catch (error) {
//         console.error("Error fetching author count:", error.message);
//       }
//     };

//     const fetchBookCount = async () => {
//       try {
//         const response = await fetch("http://localhost:8080/api/auth/book-count", {
//           method: "GET",
//           headers: {
//             "Content-Type": "application/json",
//           },
//         });

//         if (!response.ok) {
//           throw new Error(`Error fetching book count: ${response.status}`);
//         }

//         const count = await response.json();
//         setData((prevState) => ({
//           ...prevState,
//           books: count,
//         }));
//       } catch (error) {
//         console.error("Error fetching book count:", error.message);
//       }
//     };

//     getUserDetails();  // Call the function to set the username

//     fetchCategoryCount();
//     fetchAuthorCount(); // Call the function to fetch the author count
//     fetchBookCount();
//   }, []);  // This effect runs once when the component mounts

//   const handleLogout = () => {
//     localStorage.removeItem('token');
//     navigate('/');
//   };

//   return (
//     <div className="dashboard-container">
//       <div className="sidebar">
//         <ul>
//           <li onClick={() => navigate('/dashboard')}>Dashboard</li>
//           <li onClick={() => navigate('/authors')}>Authors</li>
//           <li onClick={() => navigate('/book')}>Book</li>
//           <li onClick={() => navigate('/category')}>Category</li>
//           <li onClick={handleLogout}>Logout</li>
//         </ul>
//       </div>
//       <div className="main-content">
//         <h2>Dashboard Overview</h2>
//         {username && <h3>Welcome, {username}!</h3>} {/* Display welcome message with username */}
//         <div style={{ display: "flex", justifyContent: "space-between" }}>
//           <Card title="Total Books" count={data.books} color="#4caf50" />
//           <Card title="Total Authors" count={data.authors} color="#007bff" />
//           <Card title="Total Categories" count={data.categories} color="#dc3545" />
//         </div>
//       </div>
//       <footer style={{
//         padding: '10px',
//         backgroundColor: 'rgb(149 153 157)',
//         textAlign: 'center',
//         fontSize: '14px',
//         color: 'white',
//         borderTop: '1px solid #dee2e6',
//         marginTop: 'auto',
//         width: '100%',
//         position: 'absolute',
//         bottom: '0',
//         left: '0',
//       }}>
//         <p>&copy; {new Date().getFullYear()} Diksha Nagdevate</p>
//       </footer>
//     </div>
//   );
// };

// export default Dashboard;


import React, { useState, useEffect } from "react";
import Card from "./Card"; // Import the Card component
import { useNavigate } from 'react-router-dom';

const Dashboard = () => {

  const getUserId = () => {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const payload = token.split('.')[1];  // Get the payload (second part)
        const decodedPayload = JSON.parse(atob(payload));  // Decode and parse the payload
        console.log('Decoded Payload:', decodedPayload); // Log to check the content
        console.log(decodedPayload.sub);
        return decodedPayload.sub;  // Use 'sub' field for user_id
      } catch (error) {
        console.error('Error decoding token:', error);
        return null;
      }
    }
    return null;
  };

  const userId = getUserId();
  const navigate = useNavigate();
  const [data, setData] = useState({
    books: 0,
    authors: 0,
    categories: 0,
  });
  const [username, setUsername] = useState("");  // State to store the username

  useEffect(() => {
    // Fetch user name from backend
    const fetchUserName = async () => {
      const token = localStorage.getItem('token');
      if (token) {
        try {
          const response = await fetch(`http://localhost:8080/api/auth/userName/${userId}`, {
            method: "GET",
            headers: {
              "Authorization": `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          });

          const name = await response.text();
          setUsername(name); // Set the username from the response

          if (!response.ok) {
            throw new Error(`Error fetching user name: ${response.status}`);
          }

        } catch (error) {
          console.error("Error fetching user name:", error.message);
        }
      }
    };

    // Fetch category, author, and book counts from the backend
    const fetchCategoryCount = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/auth/category-count", {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        });

        if (!response.ok) {
          throw new Error(`Error fetching category count: ${response.status}`);
        }

        const count = await response.json();
        setData((prevState) => ({
          ...prevState,
          categories: count,
        }));
      } catch (error) {
        console.error("Error fetching category count:", error.message);
      }
    };

    const fetchAuthorCount = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/auth/author-count", {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        });

        if (!response.ok) {
          throw new Error(`Error fetching author count: ${response.status}`);
        }

        const count = await response.json();
        setData((prevState) => ({
          ...prevState,
          authors: count,
        }));
      } catch (error) {
        console.error("Error fetching author count:", error.message);
      }
    };

    const fetchBookCount = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/auth/book-count", {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        });

        if (!response.ok) {
          throw new Error(`Error fetching book count: ${response.status}`);
        }

        const count = await response.json();
        setData((prevState) => ({
          ...prevState,
          books: count,
        }));
      } catch (error) {
        console.error("Error fetching book count:", error.message);
      }
    };

    fetchUserName();  // Fetch the username
    fetchCategoryCount();
    fetchAuthorCount();
    fetchBookCount();
  }, []);  // This effect runs once when the component mounts

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/');
  };

  return (
    <div className="dashboard-container">
      <div className="sidebar">
        <ul>
          <li onClick={() => navigate('/dashboard')}>Dashboard</li>
          <li onClick={() => navigate('/authors')}>Authors</li>
          <li onClick={() => navigate('/book')}>Book</li>
          <li onClick={() => navigate('/category')}>Category</li>
          <li onClick={handleLogout}>Logout</li>
        </ul>
      </div>
      <div className="main-content">
        <h2>Dashboard Overview</h2>
        {username && <h3>Welcome, {username}!</h3>} {/* Display welcome message with username */}
        <div style={{ display: "flex", justifyContent: "space-between" }}>
          <Card title="Total Books" count={data.books} color="#4caf50" />
          <Card title="Total Authors" count={data.authors} color="#007bff" />
          <Card title="Total Categories" count={data.categories} color="#dc3545" />
        </div>
      </div>
      <footer style={{
        padding: '10px',
        backgroundColor: 'rgb(149 153 157)',
        textAlign: 'center',
        fontSize: '14px',
        color: 'white',
        borderTop: '1px solid #dee2e6',
        marginTop: 'auto',
        width: '100%',
        position: 'absolute',
        bottom: '0',
        left: '0',
      }}>
        <p>&copy; {new Date().getFullYear()} Diksha Nagdevate</p>
      </footer>
    </div>
  );
};

export default Dashboard;


