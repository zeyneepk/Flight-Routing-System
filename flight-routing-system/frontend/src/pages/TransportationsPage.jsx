import React, { useEffect, useState } from "react";

const TransportationsPage = () => {
    const [transportations, setTransportations] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/api/transportations")
            .then((res) => res.json())
            .then((data) => setTransportations(data))
            .catch((err) => console.error("Hata:", err));
    }, []);

    return (
        <div className="p-4">
            <h1 className="text-xl font-bold mb-4">Transportations</h1>
            <ul className="space-y-2">
                {transportations.map((tr) => (
                    <li key={tr.id} className="border p-3 rounded shadow">
                        {tr.type} from <strong>{tr.origin.name}</strong> to <strong>{tr.destination.name}</strong>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default TransportationsPage;
