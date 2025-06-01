import React from 'react';
import { Link, Outlet, useLocation } from 'react-router-dom';

const Layout = () => {
    const location = useLocation();

    return (
        <div style={{ display: 'flex', height: '100vh' }}>
            {/* Sidebar */}
            <div style={{
                width: '200px',
                backgroundColor: '#2c3e50',
                color: 'white',
                padding: '20px',
                display: 'flex',
                flexDirection: 'column',
                gap: '10px'
            }}>
                <h2>🛫 Menu</h2>
                <Link style={{ color: location.pathname === '/locations' ? 'yellow' : 'white' }} to="/locations">📍 Locations</Link>
                <Link style={{ color: location.pathname === '/transportations' ? 'yellow' : 'white' }} to="/transportations">🚗 Transportations</Link>
                <Link style={{ color: location.pathname === '/routes' ? 'yellow' : 'white' }} to="/routes">🧭 Routes</Link>
            </div>

            {/* Content */}
            <div style={{ flex: 1, padding: '20px' }}>
                <header style={{ borderBottom: '1px solid #ccc', marginBottom: '20px' }}>
                    <h1>Flight Routing System</h1>
                </header>

                <Outlet />
            </div>
        </div>
    );
};

export default Layout;
