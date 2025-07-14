import React from 'react';
import AttendanceReport from './components/AttendanceReport';
import DepartmentInsights from './components/DepartmentInsights';
import TrendAnalysis from './components/TrendAnalysis';

function App() {
    return (
        <div className="container mx-auto p-4">
            <h1 className="text-3xl font-bold mb-8">Employee Management System</h1>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                <AttendanceReport />
                <DepartmentInsights />
                <TrendAnalysis />
            </div>
        </div>
    );
}

export default App;
