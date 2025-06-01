import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Layout from './components/Layout';
import LocationsPage from './pages/LocationsPage';
import TransportationsPage from './pages/TransportationsPage';
import RoutesPage from './pages/RoutesPage';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Layout />}>
                    <Route index element={<Navigate to="/routes" />} />
                    <Route path="/locations" element={<LocationsPage />} />
                    <Route path="/transportations" element={<TransportationsPage />} />
                    <Route path="/routes" element={<RoutesPage />} />
                </Route>
            </Routes>
        </Router>
    );
}

export default App;
