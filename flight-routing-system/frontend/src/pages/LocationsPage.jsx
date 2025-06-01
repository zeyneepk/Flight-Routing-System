import React, { useEffect, useState } from "react";

const LocationsPage = () => {
    const [locations, setLocations] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/api/locations")
            .then((res) => res.json())
            .then((data) => setLocations(data))
            .catch((err) => console.error("Hata:", err));
    }, []);

    return (
        <div className="p-4">
            <h1 className="text-xl font-bold mb-4">Locations</h1>
            <ul className="space-y-2">
                {locations.map((loc) => (
                    <li key={loc.id} className="border p-3 rounded shadow">
                        <strong>{loc.name}</strong> - {loc.city}, {loc.country} ({loc.locationCode})
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default LocationsPage;
