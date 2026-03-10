package com.wasterecyclingtracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomeController {

    @GetMapping("/")
    @ResponseBody
    public String welcome() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Waste Recycling Tracker - Backend API</title>
                    <style>
                        body {
                            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                            max-width: 1000px;
                            margin: 50px auto;
                            padding: 20px;
                            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                            color: #333;
                        }
                        .container {
                            background: white;
                            border-radius: 8px;
                            padding: 30px;
                            box-shadow: 0 10px 30px rgba(0,0,0,0.3);
                        }
                        h1 {
                            color: #667eea;
                            margin-bottom: 10px;
                        }
                        .status {
                            background: #d4edda;
                            border: 1px solid #c3e6cb;
                            color: #155724;
                            padding: 12px;
                            border-radius: 4px;
                            margin: 20px 0;
                        }
                        .api-section {
                            margin: 30px 0;
                            padding: 15px;
                            background: #f8f9fa;
                            border-left: 4px solid #667eea;
                        }
                        .endpoint {
                            font-family: monospace;
                            background: #fff3cd;
                            padding: 8px;
                            margin: 8px 0;
                            border-radius: 4px;
                            word-break: break-all;
                        }
                        a {
                            color: #667eea;
                            text-decoration: none;
                            font-weight: bold;
                        }
                        a:hover {
                            text-decoration: underline;
                        }
                        .links {
                            margin-top: 20px;
                            display: flex;
                            gap: 15px;
                        }
                        button {
                            background: #667eea;
                            color: white;
                            border: none;
                            padding: 10px 20px;
                            border-radius: 4px;
                            cursor: pointer;
                            font-size: 14px;
                        }
                        button:hover {
                            background: #764ba2;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>🌱 Waste Recycling Tracker</h1>
                        <p><strong>Spring Boot Backend API</strong></p>
                        
                        <div class="status">
                            ✅ Backend is running on port <strong>8081</strong>
                        </div>

                        <h2>📋 Available API Endpoints</h2>

                        <div class="api-section">
                            <h3>Family Endpoints</h3>
                            <p><strong>Base URL:</strong> <code>http://localhost:8081/api/family</code></p>
                            <div class="endpoint">POST /api/family</div>
                            <p>Add a new waste entry</p>
                            
                            <div class="endpoint">GET /api/family/{familyName}</div>
                            <p>Get all entries for a family</p>
                            
                            <div class="endpoint">PUT /api/family/{id}</div>
                            <p>Update a waste entry</p>
                            
                            <div class="endpoint">DELETE /api/family/{id}</div>
                            <p>Delete a waste entry</p>
                        </div>

                        <div class="api-section">
                            <h3>Center Endpoints</h3>
                            <p><strong>Base URL:</strong> <code>http://localhost:8081/api/center</code></p>
                            <div class="endpoint">GET /api/center</div>
                            <p>Get all waste entries from all families</p>
                            
                            <div class="endpoint">PUT /api/center/{id}</div>
                            <p>Update the status of a waste entry</p>
                            
                            <div class="endpoint">DELETE /api/center/{id}</div>
                            <p>Delete a recycled entry</p>
                        </div>

                        <h2>🛠️ Tools & Resources</h2>
                        <div class="links">
                            <button onclick="window.open('/h2-console', '_blank')">📊 H2 Database Console</button>
                            <button onclick="window.open('http://localhost:5173', '_blank')">💻 Frontend (Vite)</button>
                        </div>

                        <hr style="margin: 30px 0; border: none; border-top: 1px solid #ddd;">

                        <h3>📚 Documentation</h3>
                        <p>For detailed API documentation, see the README.md in the backend folder.</p>
                        <p><strong>Database:</strong> H2 in-memory database (auto-create tables enabled)</p>
                        <p><strong>ORM:</strong> JPA/Hibernate</p>
                    </div>
                </body>
                </html>
                """;
    }
}
