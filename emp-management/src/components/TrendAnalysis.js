import React, { useState, useEffect } from 'react';

const TrendAnalysis = () => {
    const [trends, setTrends] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        setError(null);
        fetch('/api/reports/trends')
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => setTrends(data))
            .catch(error => {
                console.error('Error fetching trends:', error);
                setError(error.message);
            });
    }, []);

    return (
        <div className="p-4">
            <h2 className="text-xl font-bold mb-4">Attendance Trends</h2>
            {error && <div className="text-red-500 mb-4">{error}</div>}
            <div className="grid grid-cols-1 gap-4">
                {trends.map(trend => (
                    <div key={trend.id} className="border p-4">
                        <p>Employee ID: {trend.employeeId}</p>
                        <p>Date: {new Date(trend.checkIn).toLocaleDateString()}</p>
                        <p>Status: {trend.status}</p>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default TrendAnalysis;