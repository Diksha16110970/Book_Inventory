import React from "react";

const Card = ({ title, count, color }) => {
  return (
    <div
      style={{
        backgroundColor: color,
        padding: "20px",
        borderRadius: "10px",
        boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)",
        textAlign: "center",
        color: "white",
        flex: "1",
        margin: "10px",
      }}
    >
      <h3 style={{ margin: "0", fontSize: "18px" }}>{title}</h3>
      <p style={{ fontSize: "24px", fontWeight: "bold", margin: "10px 0 0" }}>
        {count}
      </p>
    </div>
  );
};

export default Card;
