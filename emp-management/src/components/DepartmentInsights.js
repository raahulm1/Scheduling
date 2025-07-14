import React, { useState, useEffect } from 'react';

const DepartmentInsights = () => {
    const [insights, setInsights] = useState({});
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');

    useEffect(() => {
        if (startDate && endDate) {
            fetch(`/api/reports/department-rates?start=${startDate}&end=${endDate}`)
                .then(response => response.json())
                .then(data => setInsights(data))
                .catch(error => console.error('Error fetching insights:', error));
        }
    }, [startDate, endDate]);

    return (
        <div className="p-4">
            <h2 className="text-xl font-bold mb-4">Department Insights</h2>
            <div className="mb-4">
                <input
                    type="datetime-local"
                    value={startDate}
                    onChange={e => setStartDate(e.target.value)}
                    className="border p-2 mr-2"
                />
                <input
                    type="datetime-local"
                    value={endDate}
                    onChange={e => setEndDate(e.target.value)}
                    className="border p-2"
                />
            </div>
            <div className="grid grid-cols-1 gap-4">
                {Object.entries(insights).map(([deptId, rate]) => (
                    <div key={deptId} className="border p-4">
                        <p>Department ID: {deptId}</p>
                        <p>Attendance Rate: {(rate * 100).toFixed(2)}%</p>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default DepartmentInsights;
