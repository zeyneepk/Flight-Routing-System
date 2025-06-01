import React, { useEffect, useState } from 'react';
import axios from 'axios';

const RouteFinder = () => {
    const [locations, setLocations] = useState([]);
    const [origin, setOrigin] = useState('');
    const [destination, setDestination] = useState('');
    const [routes, setRoutes] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/api/locations')
            .then(res => setLocations(res.data))
            .catch(err => console.error('Lokasyonlar alınamadı', err));
    }, []);

    const findRoutes = () => {
        if (!origin || !destination) return alert('Lütfen iki lokasyon seçin.');

        axios.get('http://localhost:8080/api/routes', {
            params: { origin, destination }
        })
            .then(res => setRoutes(res.data))
            .catch(err => {
                console.error('Rota bulunamadı', err);
                setRoutes([]);
            });
    };

    return (
        <div style={{ padding: '20px', fontFamily: 'sans-serif' }}>
            <h2>✈️ Route Finder</h2>

            <div style={{ display: 'flex', gap: '20px', marginBottom: '10px' }}>
                <div>
                    <label>🛫 Departure Point:</label><br />
                    <select value={origin} onChange={(e) => setOrigin(e.target.value)}>
                        <option value="">-- Choose --</option>
                        {locations.map(loc => (
                            <option key={loc.id} value={loc.locationCode}>
                                {loc.name} ({loc.locationCode})
                            </option>
                        ))}
                    </select>
                </div>

                <div>
                    <label>🛬 Arrival Point:</label><br />
                    <select value={destination} onChange={(e) => setDestination(e.target.value)}>
                        <option value="">-- Choose --</option>
                        {locations.map(loc => (
                            <option key={loc.id} value={loc.locationCode}>
                                {loc.name} ({loc.locationCode})
                            </option>
                        ))}
                    </select>
                </div>

                <div style={{ display: 'flex', alignItems: 'end' }}>
                    <button onClick={findRoutes}>🔍 Show Routes</button>
                </div>
            </div>

            <hr />

            <h3>🧭 Routes:</h3>
            {routes.length === 0 ? (
                <p>No valid routes found.</p>
            ) : (
                routes.map((route, idx) => (
                    <div key={idx} style={{
                        border: '1px solid #ccc',
                        borderRadius: '8px',
                        padding: '10px',
                        marginBottom: '10px',
                        backgroundColor: '#f9f9f9'
                    }}>
                        <strong>Rota {idx + 1}:</strong>
                        <ul style={{ marginTop: '5px' }}>
                            {route.steps.map((step, i) => (
                                <li key={i}>
                                    {step.type} ➝ {step.origin.locationCode} → {step.destination.locationCode}
                                </li>
                            ))}
                        </ul>
                    </div>
                ))
            )}
        </div>
    );
};

export default RouteFinder;
