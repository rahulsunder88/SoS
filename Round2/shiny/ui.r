shinyUI(fluidPage(theme = "mystyle.css",
                  
                  titlePanel("Disaster Management Using Twitter"),
                  
                  sidebarLayout(
                          sidebarPanel(
                          sliderInput("Altimeter",
                                      "Height above sea level (in Meters):",
                                      min = 1,  max = 10000, value = 5500),
                          sliderInput("WaterLevel",
                                      "Water-Level (in Ft):",
                                      min = 1,  max = 20, value = 10),
                          sliderInput("Temperature",
                                      "Temperature (in Degrees):",
                                      min = 1,  max = 150, value = 45),
                          sliderInput("Barometer",
                                      "Pressure (in kilo PSascal):",
                                      min = 75,  max = 200,  value = 100),
                          sliderInput("Seismometer",
                                      "Magnitude (in Richter Scale):",
                                      min = 1,  max = 10,  value = 5)
                  ),
                          mainPanel(
                                  #plotOutput("plot") 
                                  fluidRow(
                                          
                                          column(
                                                 strong(h1(textOutput("mytext"),align = "center")),width = 10,height = 80) 
                                          
                                  )
                          )
                          
                  ),
                 
                 
                  fluidRow(
                          column(12,actionButton("predict", "Predict"),align="Center")
                  )
                  
))