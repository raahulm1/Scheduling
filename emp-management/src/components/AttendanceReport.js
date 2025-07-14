import React, { useState, useEffect } from 'react';

const AttendanceReport = () => {
    const [reports, setReports] = useState([]);
    const [page, setPage] = useState(0);
    const [employeeId, setEmployeeId] = useState('');
    const [error, setError] = useState(null);

    useEffect(() => {
        setError(null);
        fetch(`/api/reports/low-attendance?employeeId=${employeeId}&page=${page}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => setReports(data.content))
            .catch(error => {
                console.error('Error fetching reports:', error);
                setError(error.message);
            });
    }, [employeeId, page]);

    return (
        <div className="p-4">
            <h2 className="text-xl font-bold mb-4">Attendance Report</h2>
            {error && <div className="text-red-500 mb-4">{error}</div>}
            <input
                type="text"
                value={employeeId}
                onChange={e => setEmployeeId(e.target.value)}
                placeholder="Enter Employee ID"
                className="border p-2 mb-4"
            />
            <div className="grid grid-cols-1 gap-4">
                {reports.map(report => (
                    <div key={report.id} className="border p-4">
                        <p>Date: {new Date(report.checkIn).toLocaleDateString()}</p>
                        <p>Status: {report.status}</p>
                    </div>
                ))}
            </div>
            <div className="mt-4">
                <button 
                    onClick={() => setPage(page - 1)} 
                    disabled={page === 0}
                    className="mr-2 p-2 bg-blue-500 text-white disabled:bg-gray-300"
                >
                    Previous
                </button>
                <button 
                    onClick={() => setPage(page + 1)}
                    className="p-2 bg-blue-500 text-white"
                >
                    Next
                </button>
            </div>
        </div>
    );
};

export default AttendanceReport;