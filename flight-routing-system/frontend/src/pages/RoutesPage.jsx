import React, { useEffect, useState } from "react";

const RoutesPage = () => {
    const [locations, setLocations] = useState([]);
    const [origin, setOrigin] = useState("");
    const [destination, setDestination] = useState("");
    const [routes, setRoutes] = useState([]);
    const [selectedRoute, setSelectedRoute] = useState(null);

    // Lokasyonları çek
    useEffect(() => {
        fetch("http://localhost:8080/api/locations")
            .then((res) => res.json())
            .then((data) => setLocations(data))
            .catch((err) => console.error("Lokasyon hatası", err));
    }, []);

    // Rota ara
    const findRoutes = () => {
        if (!origin || !destination) {
            alert("Lütfen iki lokasyon seçin.");
            return;
        }

        fetch(`http://localhost:8080/api/routes?origin=${origin}&destination=${destination}`)
            .then((res) => res.json())
            .then((data) => setRoutes(data))
            .catch((err) => console.error("Rota hatası", err));
    };

    // Rota başlığını üret (örnek: Via Istanbul Airport (IST))
    const getRouteTitle = (steps) => {
        const flightStep = steps.find(s => s.type === "FLIGHT");
        return flightStep ? `Via ${flightStep.origin.name} (${flightStep.origin.locationCode})` : "Unnamed Route";
    };

    return (
        <div style={{ display: "flex", height: "100%", gap: "10px" }}>
            {/* Sol içerik */}
            <div style={{flex: 1}}>
                <h2>🧭 Routes</h2>

                <div style={{display: "flex", alignItems: "center", gap: "20px", marginBottom: "10px"}}>
                    <div>
                        <label>Origin:</label><br/>
                        <select value={origin} onChange={(e) => setOrigin(e.target.value)}>
                            <option value="">-- Select --</option>
                            {locations.map((loc) => (
                                <option key={loc.id} value={loc.locationCode}>
                                    {loc.name}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div>
                        <label>Destination:</label><br/>
                        <select value={destination} onChange={(e) => setDestination(e.target.value)}>
                            <option value="">-- Select --</option>
                            {locations.map((loc) => (
                                <option key={loc.id} value={loc.locationCode}>
                                    {loc.name}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div>
                        <button onClick={findRoutes} style={{marginTop: "18px"}}>
                            🔍 Find Routes
                        </button>
                    </div>
                </div>


                <h3 style={{marginTop: "20px"}}>Available Routes</h3>
                <ul style={{listStyle: "none", padding: 0}}>
                    {routes.map((route, idx) => (
                        <li
                            key={idx}
                            onClick={() => setSelectedRoute(route)}
                            style={{
                                padding: "8px",
                                margin: "5px 0",
                                background: "#eee",
                                borderRadius: "5px",
                                cursor: "pointer"
                            }}
                        >
                            {getRouteTitle(route.steps)}
                        </li>
                    ))}
                </ul>
            </div>

            {/* Sağ panel */}
            {selectedRoute && (
                <div style={{
                    width: "300px",
                    borderLeft: "1px solid #ccc",
                    padding: "20px",
                    backgroundColor: "#f9f9f9"
                }}>
                    <h3>📍 Route Details</h3>
                    <ol>
                        {selectedRoute.steps.map((step, i) => (
                            <li key={i} style={{marginBottom: "10px"}}>
                                <div><strong>{step.origin.name}</strong></div>
                                <div style={{marginLeft: "10px"}}>→ {step.type}</div>
                            </li>
                        ))}
                        <li>
                            <strong>{selectedRoute.steps[selectedRoute.steps.length - 1].destination.name}</strong>
                        </li>
                    </ol>

                    <button onClick={() => setSelectedRoute(null)} style={{marginTop: "20px"}}>
                        Close
                    </button>
                </div>
            )}
        </div>
    );
};

export default RoutesPage;
